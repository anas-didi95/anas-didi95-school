/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.AuthUtils;
import com.anasdidi.school.auth.dto.RefreshTokenReqDTO;
import com.anasdidi.school.auth.dto.RefreshTokenResDTO;
import com.anasdidi.school.auth.service.AuthService;
import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.config.VertxConfig.VertxUser;
import com.anasdidi.school.common.error.E87TokenInvalidError;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.generator.AccessRefreshTokenGenerator;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
@Named(AuthConstants.Event.AUTH_REFRESH_TOKEN)
@AllArgsConstructor
@Slf4j
class RefreshTokenService extends AuthService<RefreshTokenReqDTO, RefreshTokenResDTO> {

  private final VertxConfig vertx;
  private final PasswordEncoder passwordEncoder;
  private final AccessRefreshTokenGenerator generator;

  @Override
  protected RefreshTokenResDTO execute(RefreshTokenReqDTO in) {
    log.trace("START...");

    var user = vertx.getData(in.username(), VertxUser.class).join();
    if (user.isEmpty()) {
      log.error("Token not found! {}", in.username());
      throw new E87TokenInvalidError();
    } else if (!passwordEncoder.matches(user.get().refreshToken(), in.refreshToken())) {
      log.error("Token not matched! {}", in.username());
      throw new E87TokenInvalidError();
    }

    var token =
        AuthUtils.prepareToken(
            vertx, generator, passwordEncoder, Authentication.build(in.username()));

    log.debug("Token refreshed...{}", in.username());
    return RefreshTokenResDTO.builder().token(token).build();
  }
}
