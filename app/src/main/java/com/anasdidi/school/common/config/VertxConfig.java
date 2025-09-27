/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.common.service.CommonService;
import io.micronaut.context.annotation.Context;
import io.micronaut.core.type.Argument;
import io.micronaut.json.JsonMapper;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Singleton
@Context
@Slf4j
public class VertxConfig {

  private static final String HEADER_MDC = "__MDC";
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
                CommonResDTO res = service.process(event.getReqClass().cast(req));
                message.reply(JsonObject.mapFrom(res));
                MDC.clear();
              } catch (IOException e) {
                e.printStackTrace();
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
            new DeliveryOptions()
                .addHeader(HEADER_MDC, JsonObject.mapFrom(MDC.getCopyOfContextMap()).encode()))
        .andThen(
            reply -> {
              MDC.setContextMap(mdc);
              log.info("Vertx event requested...{},{}", event.getAddress(), reply.succeeded());
              MDC.clear();
            })
        .map(reply -> JsonObject.mapFrom(reply.body()))
        .toCompletionStage()
        .toCompletableFuture();
  }

  @PreDestroy
  void preDestroy() {
    vertx
        .close()
        .onComplete(
            event -> log.info("Vertx closed..."),
            error -> log.error("Vertx closing failed!", error));
  }
}
