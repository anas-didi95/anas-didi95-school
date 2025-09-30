/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service.impl;

import com.anasdidi.school.common.error.E02RecordAlreadyExistsError;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.entity.UserEntity;
import com.anasdidi.school.user.repository.UserRepository;
import com.anasdidi.school.user.service.UserService;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
@Named(UserConstants.Event.USER_ADD_USER)
@Transactional
@RequiredArgsConstructor
@Slf4j
class AddUserService extends UserService<AddUserReqDTO, AddUserResDTO> {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  protected AddUserResDTO execute(AddUserReqDTO in) {
    log.trace("START...");

    userRepository
        .findByUsername(in.username())
        .ifPresent(
            o -> {
              log.error("Username already exists! {}", o.getUsername());
              throw new E02RecordAlreadyExistsError("Username");
            });

    String createBy = "SYSTEM";
    UserEntity user = new UserEntity();
    user.setIsDeleted(false);
    user.setVersion(0);
    user.setCreateBy(createBy);
    user.setUpdateBy(createBy);
    user.setUsername(in.username());
    user.setPassword(passwordEncoder.encode(in.password()));
    user.setName(in.name());
    userRepository.save(user);

    log.debug("User created...{}", user.getUsername());
    return AddUserResDTO.builder().id(user.getId()).build();
  }
}
