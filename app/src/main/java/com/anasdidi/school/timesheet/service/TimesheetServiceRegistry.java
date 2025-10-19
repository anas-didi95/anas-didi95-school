/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.service;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.timesheet.TimesheetConstants.EventEnum;
import jakarta.inject.Singleton;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class TimesheetServiceRegistry {

  private final Map<String, TimesheetService<?, ?>> serviceMap;

  @SuppressWarnings("unchecked")
  public final <A extends CommonReqDTO, B extends CommonResDTO> TimesheetService<A, B> get(
      EventEnum e) {
    return (TimesheetService<A, B>) get(e.getAddress(), e.getReqClass(), e.getResClass());
  }

  @SuppressWarnings("unchecked")
  private final <A extends CommonReqDTO, B extends CommonResDTO> TimesheetService<A, B> get(
      String address, Class<A> reqClass, Class<B> resClass) {
    return (TimesheetService<A, B>) serviceMap.get(address);
  }
}
