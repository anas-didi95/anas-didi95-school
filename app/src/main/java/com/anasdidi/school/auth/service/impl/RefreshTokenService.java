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
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import io.micronaut.security.token.generator.AccessRefreshTokenGenerator;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@Singleton
@Named(AuthConstants.Event.AUTH_REFRESH_TOKEN)
@RequiredArgsConstructor
@Slf4j
class RefreshTokenService extends AuthService<RefreshTokenReqDTO, RefreshTokenResDTO> {

  private final RefreshTokenServiceProps props;
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

    var user1 =
        vertx
            .requestEvent(
                UserConstants.EventEnum.USER_GET_USER,
                JsonObject.mapFrom(GetUserReqDTO.builder().id(user.get().userId()).build()))
            .thenApply(reply -> reply.mapTo(GetUserResDTO.class))
            .join();
    var token =
        AuthUtils.prepareToken(
            props.refreshTokenExpiredSecs(), vertx, generator, passwordEncoder, user1.result());

    log.debug("Token refreshed...{}", in.username());
    return RefreshTokenResDTO.builder().token(token).build();
  }
}
