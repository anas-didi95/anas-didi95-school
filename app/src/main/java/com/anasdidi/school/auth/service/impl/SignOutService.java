/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.dto.SignOutReqDTO;
import com.anasdidi.school.auth.dto.SignOutResDTO;
import com.anasdidi.school.auth.service.AuthService;
import com.anasdidi.school.common.config.VertxConfig;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(AuthConstants.Event.AUTH_SIGN_OUT)
@RequiredArgsConstructor
@Slf4j
class SignOutService extends AuthService<SignOutReqDTO, SignOutResDTO> {

  private final VertxConfig vertx;

  @Override
  protected SignOutResDTO execute(SignOutReqDTO in) {
    log.trace("START...");

    var user =
        vertx
            .stopTimer(in.username())
            .thenCompose(o -> vertx.clearData(in.username(), VertxConfig.VertxUser.class))
            .join();
    if (user.isEmpty()) {
      log.warn("Vertx user not found! {}", in.username());
    }

    log.debug("User signed out...{}", in.username());
    return SignOutResDTO.builder().build();
  }
}
