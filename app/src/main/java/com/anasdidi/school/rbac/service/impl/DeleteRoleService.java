/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.dto.DeleteRoleReqDTO;
import com.anasdidi.school.rbac.dto.DeleteRoleResDTO;
import com.anasdidi.school.rbac.repository.RbacRepository;
import com.anasdidi.school.rbac.service.RbacService;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_DELETE_ROLE)
@Transactional(transactionManager = RbacConstants.CONNECTION_NAME)
@RequiredArgsConstructor
@Slf4j
class DeleteRoleService extends RbacService<DeleteRoleReqDTO, DeleteRoleResDTO> {

  private final RbacRepository rbacRepository;

  @Override
  protected DeleteRoleResDTO execute(DeleteRoleReqDTO in) {
    log.trace("START...");

    var id = in.id();
    var entity =
        rbacRepository
            .findById(id)
            .orElseThrow(
                () -> {
                  log.error("Role not found! {}", id);
                  throw new E03RecordNotFoundError("Role");
                });

    entity.setIsDeleted(true);
    entity = rbacRepository.update(entity);

    log.debug("Role deleted...{}", entity.getRole());
    return DeleteRoleResDTO.builder().id(id).build();
  }
}
