/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.UserConstants.EventEnum;
import com.anasdidi.school.user.controller.UserController;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.AssignUserRoleReqDTO;
import com.anasdidi.school.user.dto.AssignUserRoleResDTO;
import com.anasdidi.school.user.dto.DeleteUserReqDTO;
import com.anasdidi.school.user.dto.DeleteUserResDTO;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import com.anasdidi.school.user.dto.ListUserReqDTO;
import com.anasdidi.school.user.dto.ListUserResDTO;
import com.anasdidi.school.user.dto.UpdateUserReqDTO;
import com.anasdidi.school.user.dto.UpdateUserResDTO;
import com.anasdidi.school.user.dto.model.UserDTO;
import com.anasdidi.school.user.service.UserServiceRegistry;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Controller(CommonConstants.V1_URL + UserConstants.BASE_URL)
@RequiredArgsConstructor
class UserControllerV1 extends UserController {

  private final UserServiceRegistry registry;

  @Override
  @Post
  protected HttpResponse<AddUserResDTO> addUser(HttpRequest<?> request, @Body AddUserReqDTO body) {
    return HttpResponse.created(
        (AddUserResDTO) registry.get(EventEnum.USER_ADD_USER).process(body));
  }

  @Override
  @Get
  protected HttpResponse<ListUserResDTO> listUser(
      HttpRequest<?> request,
      @Nullable String username,
      @Nullable String name,
      @Nullable Integer pageNo,
      @Nullable Integer totalRecordsPerPage) {
    ListUserReqDTO body =
        ListUserReqDTO.builder()
            .username(username)
            .name(name)
            .pageNo(pageNo)
            .totalRecordsPerPage(totalRecordsPerPage)
            .build();
    return HttpResponse.ok((ListUserResDTO) registry.get(EventEnum.USER_LIST_USER).process(body));
  }

  @Override
  @Get("/{userId}")
  protected HttpResponse<GetUserResDTO> getUser(HttpRequest<?> request, UUID userId) {
    GetUserReqDTO body = GetUserReqDTO.builder().id(userId).build();
    return HttpResponse.ok((GetUserResDTO) registry.get(EventEnum.USER_GET_USER).process(body));
  }

  @Override
  @Post("/{userId}")
  protected HttpResponse<UpdateUserResDTO> updateUser(
      HttpRequest<?> request, @Body UserDTO update, UUID userId) {
    UpdateUserReqDTO body =
        UpdateUserReqDTO.builder().id(userId).update(request.getBody(UserDTO.class).get()).build();
    return HttpResponse.ok(
        (UpdateUserResDTO) registry.get(EventEnum.USER_UPDATE_USER).process(body));
  }

  @Override
  @Delete("/{userId}")
  protected HttpResponse<DeleteUserResDTO> deleteUser(HttpRequest<?> request, UUID userId) {
    DeleteUserReqDTO body = DeleteUserReqDTO.builder().id(userId).build();
    return HttpResponse.ok(
        (DeleteUserResDTO) registry.get(EventEnum.USER_DELETE_USER).process(body));
  }

  @Override
  @Post("/{userId}/role")
  protected HttpResponse<AssignUserRoleResDTO> assignUserRole(
      HttpRequest<?> request, @Body UserDTO update, UUID userId) {
    AssignUserRoleReqDTO body =
        AssignUserRoleReqDTO.builder()
            .id(userId)
            .version(update.version())
            .roleList(update.roleList())
            .build();
    return HttpResponse.ok(
        (AssignUserRoleResDTO) registry.get(EventEnum.USER_ASSIGN_USER_ROLE).process(body));
  }
}
