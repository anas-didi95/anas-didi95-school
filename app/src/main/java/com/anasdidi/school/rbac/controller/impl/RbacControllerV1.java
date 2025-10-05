/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.RbacConstants.EventEnum;
import com.anasdidi.school.rbac.controller.RbacController;
import com.anasdidi.school.rbac.dto.ListAccessReqDTO;
import com.anasdidi.school.rbac.dto.ListAccessResDTO;
import com.anasdidi.school.rbac.service.RbacServiceRegistry;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import lombok.RequiredArgsConstructor;

@Controller(CommonConstants.V1_URL + RbacConstants.BASE_URL)
@RequiredArgsConstructor
class RbacControllerV1 extends RbacController {

  private final RbacServiceRegistry registry;

  @Override
  @Get("/list-access")
  protected HttpResponse<ListAccessResDTO> listAccess(HttpRequest<?> request) {
    var body = ListAccessReqDTO.builder().build();
    return HttpResponse.ok(
        (ListAccessResDTO) registry.get(EventEnum.RBAC_LIST_ACCESS).process(body));
  }
}
