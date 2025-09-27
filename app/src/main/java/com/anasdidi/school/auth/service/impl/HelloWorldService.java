/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.dto.HelloWorldReqDTO;
import com.anasdidi.school.auth.dto.HelloWorldResDTO;
import com.anasdidi.school.auth.service.AuthService;
import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;

@Singleton
@Named(AuthConstants.Event.AUTH_HELLO_WORLD)
@AllArgsConstructor
class HelloWorldService extends AuthService<HelloWorldReqDTO, HelloWorldResDTO> {

  private final VertxConfig vertx;

  @Override
  protected HelloWorldResDTO execute(HelloWorldReqDTO in) {
    vertx
        .vertx()
        .eventBus()
        .publish(
            UserConstants.EventEnum.USER_GET_USER.getAddress(),
            JsonObject.mapFrom(
                GetUserReqDTO.builder()
                    .id(UUID.fromString("c5125478-8923-4c0a-a78f-1a448c62eac8"))
                    .build()),
            new DeliveryOptions()
                .addHeader("_MDC", JsonObject.mapFrom(MDC.getCopyOfContextMap()).encode()));
    return HelloWorldResDTO.builder().greeting("Hi from Auth, %s".formatted(in.name())).build();
  }
}
