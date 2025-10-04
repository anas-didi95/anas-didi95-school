/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.RbacConstants.EventEnum;
import com.anasdidi.school.rbac.controller.RbacController;
import com.anasdidi.school.rbac.dto.HelloWorldReqDTO;
import com.anasdidi.school.rbac.dto.HelloWorldResDTO;
import com.anasdidi.school.rbac.service.RbacServiceRegistry;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

@Controller(CommonConstants.V1_URL + RbacConstants.BASE_URL)
@RequiredArgsConstructor
class RbacControllerV1 extends RbacController {

  private final RbacServiceRegistry registry;

  @Override
  @Post("hello-world")
  protected HttpResponse<HelloWorldResDTO> helloWorld(
      HttpRequest<?> request, @Body HelloWorldReqDTO body) {
    return HttpResponse.ok(
        (HelloWorldResDTO) registry.get(EventEnum.RBAC_HELLO_WORLD).process(body));
  }
}
