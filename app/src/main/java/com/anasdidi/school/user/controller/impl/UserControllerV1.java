/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.UserMapper;
import com.anasdidi.school.user.controller.UserController;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import com.anasdidi.school.user.dto.SearchUserReqDTO;
import com.anasdidi.school.user.dto.SearchUserResDTO;
import com.anasdidi.school.user.dto.UpdateUserReqDTO;
import com.anasdidi.school.user.dto.UpdateUserResDTO;
import com.anasdidi.school.user.dto.model.UserDTO;
import com.anasdidi.school.user.service.UserServiceRegistry;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Controller(CommonConstants.V1_URL + UserConstants.BASE_URL)
@AllArgsConstructor
class UserControllerV1 extends UserController {

  private final UserServiceRegistry registry;
  private final UserMapper userMapper;

  @Override
  @Post
  protected HttpResponse<AddUserResDTO> addUser(HttpRequest<AddUserReqDTO> request) {
    AddUserReqDTO body = request.getBody().get();
    return HttpResponse.created(
        (AddUserResDTO) registry.get(UserConstants.ServiceEnum.USER_ADD_USER).process(body));
  }

  @Override
  @Get
  protected HttpResponse<SearchUserResDTO> searchUser(HttpRequest<Void> request) {
    SearchUserReqDTO body = userMapper.toSearchUserReqDTO(parseParameters(request));
    return HttpResponse.ok(
        (SearchUserResDTO) registry.get(UserConstants.ServiceEnum.USER_SEARCH_USER).process(body));
  }

  @Override
  @Get("/{userId}")
  protected HttpResponse<GetUserResDTO> getUser(
      HttpRequest<Void> request, @PathVariable UUID userId) {
    GetUserReqDTO body = GetUserReqDTO.builder().id(userId).build();
    return HttpResponse.ok(
        (GetUserResDTO) registry.get(UserConstants.ServiceEnum.USER_GET_USER).process(body));
  }

  @Override
  @Post("/{userId}")
  protected HttpResponse<UpdateUserResDTO> updateUser(HttpRequest<UserDTO> request, UUID userId) {
    UpdateUserReqDTO body =
        UpdateUserReqDTO.builder().id(userId).update(request.getBody(UserDTO.class).get()).build();
    return HttpResponse.ok(
        (UpdateUserResDTO) registry.get(UserConstants.ServiceEnum.USER_UPDATE_USER).process(body));
  }
}
