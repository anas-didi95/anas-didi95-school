/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.RbacMapper;
import com.anasdidi.school.rbac.dto.GetRoleReqDTO;
import com.anasdidi.school.rbac.dto.GetRoleResDTO;
import com.anasdidi.school.rbac.repository.RbacRepository;
import com.anasdidi.school.rbac.service.RbacService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_GET_ROLE)
@RequiredArgsConstructor
@Slf4j
class GetRoleService extends RbacService<GetRoleReqDTO, GetRoleResDTO> {

  private final RbacRepository rbacRepository;
  private final RbacMapper rbacMapper;

  @Override
  protected GetRoleResDTO execute(GetRoleReqDTO in) {
    log.trace("START...");

    var result =
        rbacRepository
            .findById(in.id())
            .orElseThrow(
                () -> {
                  log.error("Role not found! id={}", in.id());
                  throw new E03RecordNotFoundError("Role");
                });

    log.debug("Role found...{}");
    return GetRoleResDTO.builder().result(rbacMapper.toRoleDTO(result)).build();
  }
}
