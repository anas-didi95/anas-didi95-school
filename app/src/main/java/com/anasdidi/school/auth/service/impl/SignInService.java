/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.AuthUtils;
import com.anasdidi.school.auth.dto.SignInReqDTO;
import com.anasdidi.school.auth.dto.SignInResDTO;
import com.anasdidi.school.auth.entity.AuthEntity;
import com.anasdidi.school.auth.repository.AuthRepository;
import com.anasdidi.school.auth.service.AuthService;
import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.error.E88UserDisabledError;
import com.anasdidi.school.common.error.E89InvalidUsernamePasswordError;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.ListUserReqDTO;
import com.anasdidi.school.user.dto.ListUserResDTO;
import io.micronaut.security.token.generator.AccessRefreshTokenGenerator;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
@Named(AuthConstants.Event.AUTH_SIGN_IN)
@RequiredArgsConstructor
@Slf4j
class SignInService extends AuthService<SignInReqDTO, SignInResDTO> {

  private final VertxConfig vertx;
  private final PasswordEncoder passwordEncoder;
  private final AccessRefreshTokenGenerator generator;
  private final AuthRepository authRepository;

  @Override
  protected SignInResDTO execute(SignInReqDTO in) {
    log.trace("START...");

    var search =
        vertx
            .requestEvent(
                UserConstants.EventEnum.USER_LIST_USER,
                JsonObject.mapFrom(ListUserReqDTO.builder().username(in.username()).build()))
            .thenApply(reply -> reply.mapTo(ListUserResDTO.class))
            .join();
    if (Objects.isNull(search.resultList()) || search.resultList().isEmpty()) {
      log.error("Username not found! {}", in.username());
      throw new E89InvalidUsernamePasswordError();
    }

    var user = search.resultList().get(0);
    if (user.isDeleted()) {
      log.error("User disabled! {}", in.username());
      throw new E88UserDisabledError();
    } else if (!passwordEncoder.matches(in.password(), user.password())) {
      log.error("Wrong password! {}", in.username());
      throw new E89InvalidUsernamePasswordError();
    }

    var token = AuthUtils.prepareToken(vertx, generator, passwordEncoder, user);

    authRepository
        .findByUsername(user.username())
        .ifPresentOrElse(
            auth -> {
              auth.setRefreshToken(token.getRefreshToken());
              authRepository.update(auth);
            },
            () -> {
              AuthEntity e = new AuthEntity();
              e.setId(user.id());
              e.setUsername(user.username());
              e.setRefreshToken(token.getRefreshToken());
              authRepository.save(e);
            });

    log.debug("User signed in...{}", user.username());
    return SignInResDTO.builder().token(token).build();
  }
}
