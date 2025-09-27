/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user;

import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.user.UserConstants.EventEnum;
import com.anasdidi.school.user.service.UserService;
import com.anasdidi.school.user.service.UserServiceRegistry;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.core.type.Argument;
import io.micronaut.json.JsonMapper;
import io.vertx.core.json.JsonObject;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;

@Factory
@Context
@AllArgsConstructor
public class UserConfig {

  private final VertxConfig vertx;
  private final UserServiceRegistry registry;
  private final JsonMapper jsonMapper;

  @PostConstruct
  void postContruct() {
    for (EventEnum event : UserConstants.EventEnum.values()) {
      UserService<CommonReqDTO, CommonResDTO> service = registry.get(event);

      vertx
          .vertx()
          .eventBus()
          .<JsonObject>consumer(event.getAddress())
          .handler(
              message -> {
                try {
                  MDC.setContextMap(
                      jsonMapper.readValue(
                          message.headers().get("_MDC"),
                          Argument.mapOf(String.class, String.class)));
                } catch (IOException e) {
                  e.printStackTrace();
                }
                CommonReqDTO req = message.body().mapTo(event.getReqClass());
                CommonResDTO res = service.process(event.getReqClass().cast(req));
                message.reply(JsonObject.mapFrom(res));
                MDC.clear();
              });
    }
  }
}
