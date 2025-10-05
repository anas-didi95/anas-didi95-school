/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.AuthUtils;
import com.anasdidi.school.auth.dto.RefreshTokenReqDTO;
import com.anasdidi.school.auth.dto.RefreshTokenResDTO;
import com.anasdidi.school.auth.repository.AuthRepository;
import com.anasdidi.school.auth.service.AuthService;
import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.error.E87TokenInvalidError;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import io.micronaut.security.token.generator.AccessRefreshTokenGenerator;
import io.micronaut.security.token.jwt.validator.JWTClaimsSetUtils;
import io.micronaut.security.token.jwt.validator.JsonWebTokenParser;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.text.ParseException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
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
  private final AuthRepository authRepository;
  private final JsonWebTokenParser<?> tokenParser;

  @Override
  protected RefreshTokenResDTO execute(RefreshTokenReqDTO in) {
    log.trace("START...");

    UUID userId;
    OffsetDateTime issueDate;
    try {
      var claims =
          JWTClaimsSetUtils.jwtClaimsSetFromClaims(
              tokenParser
                  .parseClaims(in.jwt())
                  .orElseThrow(
                      () -> {
                        log.error("Fail to parse token!");
                        throw new E87TokenInvalidError();
                      }));
      userId = UUID.fromString(claims.getClaimAsString(AuthConstants.JWT_ATTR_USERID));
      issueDate =
          OffsetDateTime.ofInstant(claims.getIssueTime().toInstant(), ZoneId.systemDefault())
              .plus(props.refreshTokenLeewaySecs(), ChronoUnit.SECONDS);
    } catch (ParseException e) {
      log.error("Fail to parse token!", e);
      throw new E87TokenInvalidError();
    }

    var auth =
        authRepository
            .findById(userId)
            .orElseThrow(
                () -> {
                  log.error("Token not found! {}", userId);
                  throw new E87TokenInvalidError();
                });
    if (auth.getUpdateDate().isAfter(issueDate)) {
      log.error("Token expired before {}!, {}", auth.getUpdateDate(), issueDate);
      throw new E87TokenInvalidError();
    } else if (!auth.getRefreshToken().equals(in.refreshToken())) {
      log.error("Token not matched!");
      throw new E87TokenInvalidError();
    }

    var user =
        vertx
            .requestEvent(
                UserConstants.EventEnum.USER_GET_USER,
                JsonObject.mapFrom(GetUserReqDTO.builder().id(userId).build()))
            .thenApply(reply -> reply.mapTo(GetUserResDTO.class))
            .join();
    var token = AuthUtils.prepareToken(vertx, generator, passwordEncoder, user.result());

    auth.setRefreshToken(token.getRefreshToken());
    authRepository.update(auth);

    log.debug("Token refreshed...{}", user.result().username());
    return RefreshTokenResDTO.builder().token(token).build();
  }
}
