/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth;

import com.anasdidi.school.auth.repository.AuthRepository;
import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.common.config.VertxConfig;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.annotation.PostConstruct;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

@Factory
@Context
@RequiredArgsConstructor
@Slf4j
public class AuthConfig {

  private final VertxConfig vertx;
  private final AuthRepository authRepository;

  @Value(
      "${"
          + CommonConstants.PROP_PREFIX
          + AuthConstants.Event.AUTH_REFRESH_TOKEN
          + ".refreshTokenExpiredSecs}")
  private Long refreshTokenExpiredSecs;

  @Value(
      "${"
          + CommonConstants.PROP_PREFIX
          + AuthConstants.Event.AUTH_REFRESH_TOKEN
          + ".refreshTokenRevokedSecs}")
  private Long refreshTokenRevokedSecs;

  @PostConstruct
  void postContruct() {
    vertx.startPeriodic(
        0,
        refreshTokenRevokedSecs,
        "__REFRESH_TOKEN_REVOKE",
        (id) -> {
          MDC.clear();
          var maxValidDate =
              OffsetDateTime.now().minus(refreshTokenExpiredSecs, ChronoUnit.SECONDS);
          long deleteCount =
              authRepository.deleteAll(
                  (root, criteriaBuilder) ->
                      criteriaBuilder.lessThan(root.get("updateDate"), maxValidDate));
          log.info("Total {} token revoked...{}", deleteCount, maxValidDate);
        });
  }
}
