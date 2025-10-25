/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.RbacMapper;
import com.anasdidi.school.rbac.dto.ListRoleReqDTO;
import com.anasdidi.school.rbac.dto.ListRoleResDTO;
import com.anasdidi.school.rbac.repository.RbacRepository;
import com.anasdidi.school.rbac.service.RbacService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_LIST_ROLE)
@RequiredArgsConstructor
@Slf4j
class ListRoleService extends RbacService<ListRoleReqDTO, ListRoleResDTO> {

  private final RbacRepository rbacRepository;
  private final RbacMapper rbacMapper;

  @Override
  protected ListRoleResDTO execute(ListRoleReqDTO in) {
    log.trace("START...");

    var resultList = rbacRepository.findAll().stream().map(rbacMapper::toRoleDTO).toList();

    log.debug("List completed...");
    return ListRoleResDTO.builder().resultList(resultList).build();
  }
}
