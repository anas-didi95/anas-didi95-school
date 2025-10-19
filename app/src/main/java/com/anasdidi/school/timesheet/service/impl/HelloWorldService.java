/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.service.impl;

import com.anasdidi.school.timesheet.TimesheetConstants;
import com.anasdidi.school.timesheet.dto.HelloWorldReqDTO;
import com.anasdidi.school.timesheet.dto.HelloWorldResDTO;
import com.anasdidi.school.timesheet.service.TimesheetService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(TimesheetConstants.Event.TSHT_HELLO_WORLD)
@Slf4j
class HelloWorldService extends TimesheetService<HelloWorldReqDTO, HelloWorldResDTO> {

  @Override
  protected HelloWorldResDTO execute(HelloWorldReqDTO in) {
    log.trace("START...");

    log.info("Greeting returned...");
    return HelloWorldResDTO.builder()
        .greeting("Hi from Timesheet, %s".formatted(in.name()))
        .build();
  }
}
