/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service.impl;

import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.DeleteUserReqDTO;
import com.anasdidi.school.user.dto.DeleteUserResDTO;
import com.anasdidi.school.user.entity.UserEntity;
import com.anasdidi.school.user.repository.UserRepository;
import com.anasdidi.school.user.service.UserService;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(UserConstants.Event.USER_DELETE_USER)
@Transactional
@AllArgsConstructor
@Slf4j
class DeleteUserService extends UserService<DeleteUserReqDTO, DeleteUserResDTO> {

  private final UserRepository userRepository;

  @Override
  protected DeleteUserResDTO execute(DeleteUserReqDTO in) {
    log.trace("START...");

    UUID id = in.id();
    Optional<UserEntity> result =
        userRepository.findOne(
            (root, criteriaBuilder) -> {
              return criteriaBuilder.equal(root.get("id"), id);
            });
    if (result.isEmpty()) {
      log.error("User not found! {}", id);
      throw new E03RecordNotFoundError("User");
    }

    UserEntity entity = result.get();
    entity.setIsDeleted(true);
    userRepository.save(entity);

    log.debug("User deleted...{}", entity.getUsername());
    return DeleteUserResDTO.builder().id(id).build();
  }
}
