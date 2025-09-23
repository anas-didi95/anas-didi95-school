/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.controller;

import com.anasdidi.school.auth.dto.HelloWorldReqDTO;
import com.anasdidi.school.auth.dto.HelloWorldResDTO;
import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;

public abstract class AuthController extends CommonController {

  private static final String OPENAPI_TAG = "Auth API";

  @TraceLog
  @Operation(summary = "Hello World", tags = OPENAPI_TAG)
  protected abstract HttpResponse<HelloWorldResDTO> helloWorld(
      HttpRequest<?> request, HelloWorldReqDTO body);
}
