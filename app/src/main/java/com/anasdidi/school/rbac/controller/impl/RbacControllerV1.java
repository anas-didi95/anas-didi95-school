/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.controller.impl;

import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.RbacConstants.EventEnum;
import com.anasdidi.school.rbac.controller.RbacController;
import com.anasdidi.school.rbac.dto.AddRoleReqDTO;
import com.anasdidi.school.rbac.dto.AddRoleResDTO;
import com.anasdidi.school.rbac.dto.DeleteRoleReqDTO;
import com.anasdidi.school.rbac.dto.DeleteRoleResDTO;
import com.anasdidi.school.rbac.dto.GetRoleReqDTO;
import com.anasdidi.school.rbac.dto.GetRoleResDTO;
import com.anasdidi.school.rbac.dto.ListAccessReqDTO;
import com.anasdidi.school.rbac.dto.ListAccessResDTO;
import com.anasdidi.school.rbac.dto.ListRoleReqDTO;
import com.anasdidi.school.rbac.dto.ListRoleResDTO;
import com.anasdidi.school.rbac.dto.UpdateRoleReqDTO;
import com.anasdidi.school.rbac.dto.UpdateRoleResDTO;
import com.anasdidi.school.rbac.dto.model.RoleDTO;
import com.anasdidi.school.rbac.service.RbacServiceRegistry;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Controller(CommonConstants.V1_URL + RbacConstants.BASE_URL)
@RequiredArgsConstructor
class RbacControllerV1 extends RbacController {

  private final RbacServiceRegistry registry;

  @Override
  @Get("/access")
  protected HttpResponse<ListAccessResDTO> listAccess(HttpRequest<?> request) {
    var body = ListAccessReqDTO.builder().build();
    return HttpResponse.ok(
        (ListAccessResDTO) registry.get(EventEnum.RBAC_LIST_ACCESS).process(body));
  }

  @Override
  @Get("/role")
  protected HttpResponse<ListRoleResDTO> listRole(
      HttpRequest<?> request,
      @Nullable String role,
      @Nullable Integer pageNo,
      @Nullable Integer totalRecordsPerPage) {
    var body =
        ListRoleReqDTO.builder()
            .role(role)
            .pageNo(pageNo)
            .totalRecordsPerPage(totalRecordsPerPage)
            .build();
    return HttpResponse.ok((ListRoleResDTO) registry.get(EventEnum.RBAC_LIST_ROLE).process(body));
  }

  @Override
  @Post("/role")
  protected HttpResponse<AddRoleResDTO> addRole(HttpRequest<?> request, @Body AddRoleReqDTO body) {
    return HttpResponse.ok((AddRoleResDTO) registry.get(EventEnum.RBAC_ADD_ROLE).process(body));
  }

  @Override
  @Get("/role/{roleId}")
  protected HttpResponse<GetRoleResDTO> getRole(HttpRequest<?> request, UUID roleId) {
    var body = GetRoleReqDTO.builder().id(roleId).build();
    return HttpResponse.ok((GetRoleResDTO) registry.get(EventEnum.RBAC_GET_ROLE).process(body));
  }

  @Override
  @Put("/role/{roleId}")
  protected HttpResponse<UpdateRoleResDTO> updateRole(
      HttpRequest<?> request, @Body RoleDTO body, UUID roleId) {
    var req = UpdateRoleReqDTO.builder().id(roleId).update(body).build();
    return HttpResponse.ok(
        (UpdateRoleResDTO) registry.get(EventEnum.RBAC_UPDATE_ROLE).process(req));
  }

  @Override
  @Delete("/role/{roleId}")
  protected HttpResponse<DeleteRoleResDTO> deleteRole(HttpRequest<?> request, UUID roleId) {
    var req = DeleteRoleReqDTO.builder().id(roleId).build();
    return HttpResponse.ok(
        (DeleteRoleResDTO) registry.get(EventEnum.RBAC_DELETE_ROLE).process(req));
  }
}
