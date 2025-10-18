/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth;

import com.anasdidi.school.auth.entity.AuthEntity_;
import com.anasdidi.school.auth.repository.AuthRepository;
import com.anasdidi.school.common.CommonConstants;
import com.anasdidi.school.common.config.VertxConfig;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.annotation.PreDestroy;
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

  private static final String REVOKE_KEY = "__REFRESH_TOKEN_REVOKE";
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

  @Bean
  ApplicationEventListener<StartupEvent> onStartupEvent() {
    return event -> {
      vertx.startPeriodic(
          10,
          refreshTokenRevokedSecs,
          REVOKE_KEY,
          (id) -> {
            MDC.clear();
            var maxValidDate =
                OffsetDateTime.now().minus(refreshTokenExpiredSecs, ChronoUnit.SECONDS);
            long deleteCount =
                authRepository.deleteAll(
                    (root, criteriaBuilder) ->
                        criteriaBuilder.lessThan(root.get(AuthEntity_.UPDATE_DATE), maxValidDate));
            log.info("Total {} token revoked...{}", deleteCount, maxValidDate);
          });
    };
  }

  @PreDestroy
  void preDestroy() {
    vertx.stopTimer(REVOKE_KEY);
  }
}
