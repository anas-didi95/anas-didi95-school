/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.common.error.E02RecordAlreadyExistsError;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.dto.AddRoleReqDTO;
import com.anasdidi.school.rbac.dto.AddRoleResDTO;
import com.anasdidi.school.rbac.entity.RbacEntity;
import com.anasdidi.school.rbac.entity.RbacEntity_;
import com.anasdidi.school.rbac.repository.RbacRepository;
import com.anasdidi.school.rbac.service.RbacService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_ADD_ROLE)
@RequiredArgsConstructor
@Slf4j
class AddRoleService extends RbacService<AddRoleReqDTO, AddRoleResDTO> {

  private final RbacRepository rbacRepository;

  @Override
  protected AddRoleResDTO execute(AddRoleReqDTO in) {
    log.trace("START...");

    var result =
        rbacRepository.findOne(
            (root, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(RbacEntity_.ROLE), in.role()));
    if (result.isPresent()) {
      log.error("Role already existed...{}", in.role());
      throw new E02RecordAlreadyExistsError("Role");
    }

    RbacEntity entity = new RbacEntity();
    entity.setVersion(0);
    entity.setIsDeleted(false);
    entity.setRole(in.role().toUpperCase());
    entity.setAccessList(in.accessList());
    entity.setIsSuperadmin(false);
    entity = rbacRepository.save(entity);

    log.debug("Role added...{}", in.role());
    return AddRoleResDTO.builder().id(entity.getId()).build();
  }
}
