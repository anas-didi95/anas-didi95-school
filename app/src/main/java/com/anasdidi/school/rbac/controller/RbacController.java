/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.controller;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import com.anasdidi.school.rbac.dto.ListAccessResDTO;
import com.anasdidi.school.rbac.dto.ListRoleResDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;

public abstract class RbacController extends CommonController {

  private static final String OPENAPI_TAG = "RBAC API";

  @TraceLog
  @Operation(summary = "List access", tags = OPENAPI_TAG)
  protected abstract HttpResponse<ListAccessResDTO> listAccess(HttpRequest<?> request);

  @TraceLog
  @Operation(summary = "List role", tags = OPENAPI_TAG)
  protected abstract HttpResponse<ListRoleResDTO> listRole(HttpRequest<?> request);
}
