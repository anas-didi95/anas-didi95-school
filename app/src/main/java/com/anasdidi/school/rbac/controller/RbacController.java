/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.controller;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import com.anasdidi.school.rbac.dto.AddRoleReqDTO;
import com.anasdidi.school.rbac.dto.AddRoleResDTO;
import com.anasdidi.school.rbac.dto.DeleteRoleResDTO;
import com.anasdidi.school.rbac.dto.GetRoleResDTO;
import com.anasdidi.school.rbac.dto.ListAccessResDTO;
import com.anasdidi.school.rbac.dto.ListRoleResDTO;
import com.anasdidi.school.rbac.dto.UpdateRoleResDTO;
import com.anasdidi.school.rbac.dto.model.RoleDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;

public abstract class RbacController extends CommonController {

  private static final String OPENAPI_TAG = "RBAC API";

  @TraceLog
  @Operation(summary = "List Access", tags = OPENAPI_TAG)
  protected abstract HttpResponse<ListAccessResDTO> listAccess(HttpRequest<?> request);

  @TraceLog
  @Operation(summary = "List Role", tags = OPENAPI_TAG)
  protected abstract HttpResponse<ListRoleResDTO> listRole(
      HttpRequest<?> request, String role, Integer pageNo, Integer totalRecordsPerPage);

  @TraceLog
  @Operation(summary = "Add Role", tags = OPENAPI_TAG)
  protected abstract HttpResponse<AddRoleResDTO> addRole(
      HttpRequest<?> request, AddRoleReqDTO body);

  @TraceLog
  @Operation(summary = "Get Role", tags = OPENAPI_TAG)
  protected abstract HttpResponse<GetRoleResDTO> getRole(HttpRequest<?> request, UUID roleId);

  @TraceLog
  @Operation(summary = "Update Role", tags = OPENAPI_TAG)
  protected abstract HttpResponse<UpdateRoleResDTO> updateRole(
      HttpRequest<?> request, RoleDTO body, UUID roleId);

  @TraceLog
  @Operation(summary = "Delete Role", tags = OPENAPI_TAG)
  protected abstract HttpResponse<DeleteRoleResDTO> deleteRole(HttpRequest<?> request, UUID roleId);
}
