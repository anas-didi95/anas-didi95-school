/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.config.VertxConfig.VertxAccess;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.rbac.RbacConstants.EventEnum;
import com.anasdidi.school.rbac.service.RbacService;
import com.anasdidi.school.rbac.service.RbacServiceRegistry;
import com.anasdidi.school.timesheet.TimesheetConstants;
import com.anasdidi.school.user.UserConstants;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
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

    Set<CommonEvent> eventSet = new HashSet<>();
    eventSet.addAll(Arrays.asList(UserConstants.EventEnum.values()));
    eventSet.addAll(Arrays.asList(AuthConstants.EventEnum.values()));
    eventSet.addAll(Arrays.asList(RbacConstants.EventEnum.values()));
    eventSet.addAll(Arrays.asList(TimesheetConstants.EventEnum.values()));
    vertx.putData(
        VertxConfig.ACCESS_SET_KEY,
        VertxAccess.builder()
            .accessSet(eventSet.stream().map(o -> o.getAddress()).collect(Collectors.toSet()))
            .build());
  }
}
