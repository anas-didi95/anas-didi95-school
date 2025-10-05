/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service.impl;

import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.UpdateUserReqDTO;
import com.anasdidi.school.user.dto.UpdateUserResDTO;
import com.anasdidi.school.user.entity.UserEntity;
import com.anasdidi.school.user.repository.UserRepository;
import com.anasdidi.school.user.service.UserService;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(UserConstants.Event.USER_UPDATE_USER)
@Transactional(transactionManager = UserConstants.CONNECTION_NAME)
@RequiredArgsConstructor
@Slf4j
class UpdateUserService extends UserService<UpdateUserReqDTO, UpdateUserResDTO> {

  private final UserRepository userRepository;

  @Override
  protected UpdateUserResDTO execute(UpdateUserReqDTO in) {
    log.trace("START...");

    UUID id = in.id();
    int version = in.update().version();
    Optional<UserEntity> result =
        userRepository.findOne(
            (root, criteriaBuilder) -> {
              List<Predicate> list = new ArrayList<>();
              list.add(criteriaBuilder.equal(root.get("id"), id));
              list.add(criteriaBuilder.equal(root.get("version"), version));
              return criteriaBuilder.and(list.toArray(Predicate[]::new));
            });
    if (result.isEmpty()) {
      log.error("User not found! id={}, version={}", id, version);
      throw new E03RecordNotFoundError("User");
    }

    UserEntity entity = result.get();
    entity.setName(in.update().name());
    userRepository.update(entity);

    log.debug("User updated...{}", entity.getUsername());
    return UpdateUserResDTO.builder().id(id).build();
  }
}
