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
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;

@Factory
@Context
@AllArgsConstructor
public class UserConfig {

  private final VertxConfig vertx;
  private final UserServiceRegistry registry;

  @PostConstruct
  void postContruct() {
    for (EventEnum event : UserConstants.EventEnum.values()) {
      UserService<CommonReqDTO, CommonResDTO> service = registry.get(event);
      vertx.registerEvent(event, service);
    }
  }
}
