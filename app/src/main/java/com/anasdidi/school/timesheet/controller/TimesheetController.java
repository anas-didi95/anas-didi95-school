/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.controller;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import com.anasdidi.school.timesheet.dto.CheckInResDTO;
import com.anasdidi.school.timesheet.dto.CheckOutResDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;

public abstract class TimesheetController extends CommonController {

  private static final String OPENAPI_TAG = "Timesheet API";

  @TraceLog
  @Operation(summary = "Check in", tags = OPENAPI_TAG)
  protected abstract HttpResponse<CheckInResDTO> checkIn(HttpRequest<?> request);

  @TraceLog
  @Operation(summary = "Check out", tags = OPENAPI_TAG)
  protected abstract HttpResponse<CheckOutResDTO> checkOut(HttpRequest<?> request);
}
