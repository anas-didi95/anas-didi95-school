/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import com.anasdidi.school.common.dto.Views;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.AssignUserRoleResDTO;
import com.anasdidi.school.user.dto.DeleteUserResDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import com.anasdidi.school.user.dto.ListUserResDTO;
import com.anasdidi.school.user.dto.UpdateUserReqDTO;
import com.anasdidi.school.user.dto.UpdateUserResDTO;
import com.anasdidi.school.user.dto.model.UserDTO;
import com.fasterxml.jackson.annotation.JsonView;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;

public abstract class UserController extends CommonController {

  private static final String OPENAPI_TAG = "User API";

  @TraceLog
  @Operation(summary = "Add User", tags = OPENAPI_TAG)
  protected abstract HttpResponse<AddUserResDTO> addUser(
      HttpRequest<?> request, AddUserReqDTO body);

  @TraceLog
  @JsonView(Views.Public.class)
  @Operation(summary = "List User", tags = OPENAPI_TAG)
  protected abstract HttpResponse<ListUserResDTO> listUser(
      HttpRequest<?> request,
      String username,
      String name,
      Integer pageNo,
      Integer totalRecordsPerPage);

  @TraceLog
  @JsonView(Views.Public.class)
  @Operation(summary = "Get User", tags = OPENAPI_TAG)
  protected abstract HttpResponse<GetUserResDTO> getUser(HttpRequest<?> request, UUID userId);

  @TraceLog
  @Operation(summary = "Update User", tags = OPENAPI_TAG)
  protected abstract HttpResponse<UpdateUserResDTO> updateUser(
      HttpRequest<?> request, UpdateUserReqDTO.Payload payload, UUID userId);

  @TraceLog
  @Operation(summary = "Delete User", tags = OPENAPI_TAG)
  protected abstract HttpResponse<DeleteUserResDTO> deleteUser(HttpRequest<?> request, UUID userId);

  @TraceLog
  @Operation(summary = "Assign User Role", tags = OPENAPI_TAG)
  protected abstract HttpResponse<AssignUserRoleResDTO> assignUserRole(
      HttpRequest<?> request, UserDTO update, UUID userId);
}
