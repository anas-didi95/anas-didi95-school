/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.common.error.E86AccessDeniedError;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.dto.CheckAccessReqDTO;
import com.anasdidi.school.rbac.dto.CheckAccessResDTO;
import com.anasdidi.school.rbac.entity.RbacEntity_;
import com.anasdidi.school.rbac.repository.RbacRepository;
import com.anasdidi.school.rbac.service.RbacService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_CHECK_ACCESS)
@RequiredArgsConstructor
@Slf4j
class CheckAccessService extends RbacService<CheckAccessReqDTO, CheckAccessResDTO> {

  private final RbacRepository rbacRepository;

  @Override
  protected CheckAccessResDTO execute(CheckAccessReqDTO in) {
    log.trace("START...");

    var result =
        rbacRepository.findOne(
            (root, criteriaBuilder) -> {
              List<Predicate> list = new ArrayList<>();
              list.add(root.get(RbacEntity_.ROLE).in(in.roleList()));
              list.add(
                  criteriaBuilder.or(
                      criteriaBuilder.isTrue(root.get(RbacEntity_.IS_SUPERADMIN)),
                      criteriaBuilder.like(
                          root.get(RbacEntity_.ACCESS_LIST), "%" + in.access() + "%")));
              return criteriaBuilder.and(list.toArray(Predicate[]::new));
            });
    if (result.isEmpty()) {
      log.error("Access {} not assigned to any roles! {}", in.access(), in.roleList());
      throw new E86AccessDeniedError();
    }

    log.debug("Access validated...{}", in.access());
    return CheckAccessResDTO.builder().isValid(true).build();
  }
}
