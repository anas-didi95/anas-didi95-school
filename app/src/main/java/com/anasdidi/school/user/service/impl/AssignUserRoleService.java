/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service.impl;

import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.AssignUserRoleReqDTO;
import com.anasdidi.school.user.dto.AssignUserRoleResDTO;
import com.anasdidi.school.user.entity.UserEntity_;
import com.anasdidi.school.user.repository.UserRepository;
import com.anasdidi.school.user.service.UserService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(UserConstants.Event.USER_ASSIGN_USER_ROLE)
@RequiredArgsConstructor
@Slf4j
class AssignUserRoleService extends UserService<AssignUserRoleReqDTO, AssignUserRoleResDTO> {

  private final UserRepository userRepository;

  @Override
  protected AssignUserRoleResDTO execute(AssignUserRoleReqDTO in) {
    log.trace("START...");

    var entity =
        userRepository
            .findOne(
                (root, criteriaBuilder) ->
                    criteriaBuilder.and(
                        criteriaBuilder.equal(root.get(UserEntity_.ID), in.id()),
                        criteriaBuilder.equal(root.get(UserEntity_.VERSION), in.version())))
            .orElseThrow(
                () -> {
                  log.error("User not found! id={}, version={}", in.id(), in.version());
                  throw new E03RecordNotFoundError("User");
                });

    entity.setRoleList(Optional.ofNullable(in.roleList()).orElse(List.of()));
    entity = userRepository.update(entity);

    log.debug("Assigned role...", in.roleList());
    return AssignUserRoleResDTO.builder().id(entity.getId()).build();
  }
}
