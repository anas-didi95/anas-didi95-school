/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.controller.UserController;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

@Controller(CommonConstants.V1_URL + UserConstants.BASE_URL)
class UserControllerV1 extends UserController {

  @Override
  @Get
  protected HttpResponse<String> helloWorld() {
    return HttpResponse.ok("Hi!");
  }
}
