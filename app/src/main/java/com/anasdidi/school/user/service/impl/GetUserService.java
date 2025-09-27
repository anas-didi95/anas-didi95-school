/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service.impl;

import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.UserMapper;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import com.anasdidi.school.user.dto.model.UserDTO;
import com.anasdidi.school.user.entity.UserEntity;
import com.anasdidi.school.user.repository.UserRepository;
import com.anasdidi.school.user.service.UserService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(UserConstants.Event.USER_GET_USER)
@AllArgsConstructor
@Slf4j
class GetUserService extends UserService<GetUserReqDTO, GetUserResDTO> {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  protected GetUserResDTO execute(GetUserReqDTO in) {
    log.trace("START...");

    Optional<UserEntity> result = userRepository.findById(in.id());
    if (result.isEmpty()) {
      log.error("User not found! {}", in.id());
      throw new E03RecordNotFoundError("User");
    }
    UserDTO user = userMapper.toUserDTO(result.get());

    log.debug("User found...{}", user.username());
    return GetUserResDTO.builder().result(user).build();
  }
}
