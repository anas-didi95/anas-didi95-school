/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet;

import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.timesheet.TimesheetConstants.EventEnum;
import com.anasdidi.school.timesheet.service.TimesheetService;
import com.anasdidi.school.timesheet.service.TimesheetServiceRegistry;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Factory
@Context
@RequiredArgsConstructor
public class TimesheetConfig {

  private final VertxConfig vertx;
  private final TimesheetServiceRegistry registry;

  @PostConstruct
  void postContruct() {
    for (EventEnum event : TimesheetConstants.EventEnum.values()) {
      TimesheetService<CommonReqDTO, CommonResDTO> service = registry.get(event);
      vertx.registerEvent(event, service);
    }
  }
}
