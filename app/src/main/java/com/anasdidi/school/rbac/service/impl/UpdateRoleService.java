/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.dto.UpdateRoleReqDTO;
import com.anasdidi.school.rbac.dto.UpdateRoleResDTO;
import com.anasdidi.school.rbac.entity.RbacEntity_;
import com.anasdidi.school.rbac.repository.RbacRepository;
import com.anasdidi.school.rbac.service.RbacService;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_UPDATE_ROLE)
@Transactional(transactionManager = RbacConstants.CONNECTION_NAME)
@RequiredArgsConstructor
@Slf4j
class UpdateRoleService extends RbacService<UpdateRoleReqDTO, UpdateRoleResDTO> {

  private final RbacRepository rbacRepository;

  @Override
  protected UpdateRoleResDTO execute(UpdateRoleReqDTO in) {
    log.trace("START...");

    var id = in.id();
    var version = in.update().version();
    var entity =
        rbacRepository
            .findOne(
                (root, criteriaBuilder) ->
                    criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(RbacEntity_.ID), id),
                        criteriaBuilder.equal(root.get(RbacEntity_.VERSION), version)))
            .orElseThrow(
                () -> {
                  log.error("Role not found! id={}, version={}", id, version);
                  throw new E03RecordNotFoundError("Role");
                });

    entity.setAccessList(in.update().accessList());
    entity = rbacRepository.update(entity);

    log.info("Role updated...{}", entity.getRole());
    return UpdateRoleResDTO.builder().id(id).build();
  }
}
