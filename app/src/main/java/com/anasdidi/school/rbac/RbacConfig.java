/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac;

import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.rbac.RbacConstants.EventEnum;
import com.anasdidi.school.rbac.service.RbacService;
import com.anasdidi.school.rbac.service.RbacServiceRegistry;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Factory
@Context
@RequiredArgsConstructor
public class RbacConfig {

  private final VertxConfig vertx;
  private final RbacServiceRegistry registry;

  @PostConstruct
  void postContruct() {
    for (EventEnum event : RbacConstants.EventEnum.values()) {
      RbacService<CommonReqDTO, CommonResDTO> service = registry.get(event);
      vertx.registerEvent(event, service);
    }
  }
}
