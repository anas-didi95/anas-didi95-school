/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.controller.UserController;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.service.UserServiceRegistry;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.AllArgsConstructor;

@Controller(CommonConstants.V1_URL + UserConstants.BASE_URL)
@AllArgsConstructor
class UserControllerV1 extends UserController {

  private final UserServiceRegistry registry;

  @Override
  @Post
  protected HttpResponse<AddUserResDTO> addUser(@Body AddUserReqDTO body) {
    return HttpResponse.created(
        (AddUserResDTO) registry.get(UserConstants.ServiceEnum.USER_ADD_USER).process(body));
  }
}
