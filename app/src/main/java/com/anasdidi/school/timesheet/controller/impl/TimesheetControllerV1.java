/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.timesheet.TimesheetConstants;
import com.anasdidi.school.timesheet.TimesheetConstants.EventEnum;
import com.anasdidi.school.timesheet.controller.TimesheetController;
import com.anasdidi.school.timesheet.dto.CheckInReqDTO;
import com.anasdidi.school.timesheet.dto.CheckInResDTO;
import com.anasdidi.school.timesheet.dto.CheckOutReqDTO;
import com.anasdidi.school.timesheet.dto.CheckOutResDTO;
import com.anasdidi.school.timesheet.dto.HelloWorldReqDTO;
import com.anasdidi.school.timesheet.dto.HelloWorldResDTO;
import com.anasdidi.school.timesheet.service.TimesheetServiceRegistry;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

@Controller(CommonConstants.V1_URL + TimesheetConstants.BASE_URL)
@RequiredArgsConstructor
class TimesheetControllerV1 extends TimesheetController {

  private final TimesheetServiceRegistry registry;

  @Override
  @Post("/helloWorld")
  protected HttpResponse<HelloWorldResDTO> helloWorld(
      HttpRequest<?> request, @Body HelloWorldReqDTO body) {
    return HttpResponse.ok(
        (HelloWorldResDTO) registry.get(EventEnum.TSHT_HELLO_WORLD).process(body));
  }

  @Override
  @Get("/check-in")
  protected HttpResponse<CheckInResDTO> checkIn(HttpRequest<?> request) {
    return HttpResponse.ok(
        (CheckInResDTO)
            registry.get(EventEnum.TSHT_CHECK_IN).process(CheckInReqDTO.builder().build()));
  }

  @Override
  @Get("/check-out")
  protected HttpResponse<CheckOutResDTO> checkOut(HttpRequest<?> request) {
    return HttpResponse.ok(
        (CheckOutResDTO)
            registry.get(EventEnum.TSHT_CHECK_OUT).process(CheckOutReqDTO.builder().build()));
  }
}
