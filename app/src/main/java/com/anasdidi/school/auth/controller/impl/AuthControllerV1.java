/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.controller.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.AuthConstants.ServiceEnum;
import com.anasdidi.school.auth.controller.AuthController;
import com.anasdidi.school.auth.dto.HelloWorldReqDTO;
import com.anasdidi.school.auth.dto.HelloWorldResDTO;
import com.anasdidi.school.auth.service.AuthServiceRegistry;
import com.anasdidi.school.common.CommonConstants;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.AllArgsConstructor;

@Controller(CommonConstants.V1_URL + AuthConstants.BASE_URL)
@AllArgsConstructor
class AuthControllerV1 extends AuthController {

  private final AuthServiceRegistry registry;

  @Override
  @Post
  protected HttpResponse<HelloWorldResDTO> helloWorld(
      HttpRequest<?> request, @Body HelloWorldReqDTO body) {
    return HttpResponse.ok(
        (HelloWorldResDTO) registry.get(ServiceEnum.AUTH_HELLO_WORLD).process(body));
  }
}
