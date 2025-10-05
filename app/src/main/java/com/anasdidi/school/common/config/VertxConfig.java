/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.common.error.BaseError;
import com.anasdidi.school.common.service.CommonService;
import io.micronaut.context.annotation.Context;
import io.micronaut.core.type.Argument;
import io.micronaut.json.JsonMapper;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.time.Duration;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Singleton
@Context
@Slf4j
public class VertxConfig {

  private static final String HEADER_MDC = "__MDC";
  private static final int ERROR_CODE = 98;
  public static final String ACCESS_SET_KEY = "__ACC_SET";
  private final Vertx vertx;
  private final JsonMapper jsonMapper;

  VertxConfig(JsonMapper jsonMapper) {
    this.jsonMapper = jsonMapper;

    this.vertx = Vertx.vertx();
    DatabindCodec.mapper().findAndRegisterModules();
    log.info("Vertx started...");
  }

  public void registerEvent(CommonEvent event, CommonService<CommonReqDTO, CommonResDTO> service) {
    vertx
        .eventBus()
        .<JsonObject>consumer(event.getAddress())
        .handler(
            message -> {
              try {
                MDC.setContextMap(
                    jsonMapper.readValue(
                        message.headers().get(HEADER_MDC),
                        Argument.mapOf(String.class, String.class)));
                CommonReqDTO req = message.body().mapTo(event.getReqClass());
                CommonResDTO res = service.process(event.getReqClass().cast(req), false);
                message.reply(JsonObject.mapFrom(res));
              } catch (IOException e) {
                log.error("Fail to parse header!", e);
                message.fail(ERROR_CODE, e.getMessage());
              } catch (BaseError e) {
                log.error("Vertx event {} failed! {}", event.getAddress(), e.getMessage());
                message.fail(
                    ERROR_CODE,
                    new JsonObject()
                        .put(BaseError.PARAM_ERROR, e.error)
                        .put(BaseError.PARAM_VARIABLES, e.variables)
                        .encode());
              } finally {
                // MDC.clear();
              }
            });
    log.info("Vertx event registered...{}", event.getAddress());
  }

  public void publishEvent(CommonEvent event, JsonObject message) {
    vertx
        .eventBus()
        .publish(
            event.getAddress(),
            message,
            new DeliveryOptions()
                .addHeader(HEADER_MDC, JsonObject.mapFrom(MDC.getCopyOfContextMap()).encode()));
    log.info("Vertx event published...{}", event.getAddress());
  }

  public CompletableFuture<JsonObject> requestEvent(CommonEvent event, JsonObject message) {
    var mdc = MDC.getCopyOfContextMap();
    return vertx
        .eventBus()
        .request(
            event.getAddress(),
            message,
            new DeliveryOptions().addHeader(HEADER_MDC, JsonObject.mapFrom(mdc).encode()))
        .andThen(
            reply -> {
              MDC.setContextMap(mdc);
              log.info("Vertx event requested...{},{}", event.getAddress(), reply.succeeded());
              // MDC.clear();
            })
        .map(reply -> JsonObject.mapFrom(reply.body()))
        .toCompletionStage()
        .toCompletableFuture();
  }

  public CompletableFuture<Void> putData(String key, VertxData value) {
    var mdc = MDC.getCopyOfContextMap();
    var mapName = value.getClass().getSimpleName();

    return vertx
        .sharedData()
        .getAsyncMap(mapName)
        .compose(map -> map.put(key, JsonObject.mapFrom(value).encode()))
        .onComplete(
            event -> {
              MDC.setContextMap(mdc);
              log.info("Vertx data put...{},{},{}", mapName, key, event.succeeded());
              // MDC.clear();
            })
        .toCompletionStage()
        .toCompletableFuture();
  }

  public <A extends VertxData> CompletableFuture<Optional<A>> getData(String key, Class<A> clazz) {
    var mdc = MDC.getCopyOfContextMap();
    var mapName = clazz.getSimpleName();

    return vertx
        .sharedData()
        .getAsyncMap(mapName)
        .compose(map -> map.get(key))
        .map(v -> Optional.ofNullable(v).map(vv -> new JsonObject((String) vv).mapTo(clazz)))
        .andThen(
            event -> {
              MDC.setContextMap(mdc);
              log.info("Vertx data get...{},{},{}", mapName, key, event.succeeded());
              // MDC.clear();
            })
        .toCompletionStage()
        .toCompletableFuture();
  }

  public <A extends VertxData> CompletableFuture<Optional<A>> clearData(
      String key, Class<A> clazz) {
    var mdc = MDC.getCopyOfContextMap();
    var mapName = clazz.getSimpleName();

    return vertx
        .sharedData()
        .getAsyncMap(mapName)
        .compose(map -> map.remove(key))
        .map(v -> Optional.ofNullable(v).map(vv -> new JsonObject((String) vv).mapTo(clazz)))
        .andThen(
            event -> {
              MDC.setContextMap(mdc);
              log.info("Vertx data remove...{},{},{}", mapName, key, event.succeeded());
              // MDC.clear();
            })
        .toCompletionStage()
        .toCompletableFuture();
  }

  public CompletableFuture<Void> startTimer(long seconds, String key, Handler<Long> handler) {
    var mdc = MDC.getCopyOfContextMap();
    var mapName = VertxTimer.class.getSimpleName();
    var id = vertx.setTimer(Duration.ofSeconds(seconds).toMillis(), handler);
    var data = VertxTimer.builder().id(id).type(VertxTimer.TimerType.ONETIME).build();

    return vertx
        .sharedData()
        .getAsyncMap(mapName)
        .compose(map -> map.put(key, JsonObject.mapFrom(data).encode()))
        .onComplete(
            event -> {
              MDC.setContextMap(mdc);
              log.info(
                  "Vertx timer start...{},{},{},{},{}",
                  data.type,
                  mapName,
                  key,
                  seconds,
                  event.succeeded());
              // MDC.clear();
            })
        .toCompletionStage()
        .toCompletableFuture();
  }

  public CompletableFuture<Optional<VertxTimer>> stopTimer(String key) {
    var mdc = MDC.getCopyOfContextMap();
    var mapName = VertxTimer.class.getSimpleName();

    return vertx
        .sharedData()
        .getAsyncMap(mapName)
        .compose(map -> map.remove(key))
        .map(
            v ->
                Optional.ofNullable(v)
                    .map(vv -> new JsonObject((String) vv).mapTo(VertxTimer.class)))
        .onComplete(
            event -> {
              MDC.setContextMap(mdc);
              event
                  .result()
                  .ifPresentOrElse(
                      t -> {
                        var stop = vertx.cancelTimer(t.id());
                        log.info(
                            "Vertx timer stop...{},{},{},{},{}",
                            mapName,
                            key,
                            stop,
                            event.succeeded());
                      },
                      () ->
                          log.info("Vertx time stop...{},{},{}", mapName, key, event.succeeded()));
              // MDC.clear();
            })
        .toCompletionStage()
        .toCompletableFuture();
  }

  public void startPeriodic(
      long initialDelaySecs, long delaySecs, String key, Handler<Long> handler) {
    var mdc = MDC.getCopyOfContextMap();
    var mapName = VertxTimer.class.getSimpleName();
    var id =
        vertx.setPeriodic(
            Duration.ofSeconds(initialDelaySecs).toMillis(),
            Duration.ofSeconds(delaySecs).toMillis(),
            handler);
    var data = VertxTimer.builder().id(id).type(VertxTimer.TimerType.PERIODIC).build();

    vertx
        .sharedData()
        .getAsyncMap(mapName)
        .compose(map -> map.put(key, JsonObject.mapFrom(data).encode()))
        .onComplete(
            event -> {
              MDC.setContextMap(mdc);
              log.info(
                  "Vertx timer start...{},{},{},{},{},{}",
                  data.type,
                  mapName,
                  key,
                  initialDelaySecs,
                  delaySecs,
                  event.succeeded());
              // MDC.clear();
            })
        .toCompletionStage()
        .toCompletableFuture();
  }

  public interface VertxData {}
  ;

  @Builder
  public record VertxUser(String refreshToken, UUID userId) implements VertxData {}

  @Builder
  public record VertxTimer(long id, TimerType type) implements VertxData {
    enum TimerType {
      ONETIME,
      PERIODIC
    }
  }

  @Builder
  public record VertxAccess(Set<String> accessSet) implements VertxData {}

  @PreDestroy
  void preDestroy() {
    vertx
        .close()
        .onComplete(
            event -> log.info("Vertx closed..."),
            error -> log.error("Vertx closing failed!", error));
  }
}
