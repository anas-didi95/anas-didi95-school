/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.controller;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import com.anasdidi.school.timesheet.dto.HelloWorldReqDTO;
import com.anasdidi.school.timesheet.dto.HelloWorldResDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;

public abstract class TimesheetController extends CommonController {

  private static final String OPENAPI_TAG = "Timesheet API";

  @TraceLog
  @Operation(summary = "Hello world", tags = OPENAPI_TAG)
  protected abstract HttpResponse<HelloWorldResDTO> helloWorld(
      HttpRequest<?> request, HelloWorldReqDTO body);
}
