/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet;

import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.timesheet.dto.HelloWorldReqDTO;
import com.anasdidi.school.timesheet.dto.HelloWorldResDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TimesheetConstants {

  @UtilityClass
  public static class Event {
    public static final String TSHT_HELLO_WORLD = "TSHT_HELLO_WORLD";
  }

  @Getter
  @RequiredArgsConstructor
  public enum EventEnum implements CommonEvent {
    TSHT_HELLO_WORLD(Event.TSHT_HELLO_WORLD, HelloWorldReqDTO.class, HelloWorldResDTO.class);

    private final String address;
    private final Class<? extends CommonReqDTO> reqClass;
    private final Class<? extends CommonResDTO> resClass;
  }

  public static final String BASE_URL = "/timesheet";
  // public static final String CONNECTION_NAME = "CN-USER";
}
