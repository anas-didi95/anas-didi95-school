/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import com.nimbusds.jwt.JWTClaimsSet;
import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import io.micronaut.security.token.jwt.validator.GenericJwtClaimsValidator;
import io.micronaut.security.token.jwt.validator.JWTClaimsSetUtils;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Factory
@RequiredArgsConstructor
@Slf4j
public class CommonConfig {

  private final VertxConfig vertx;

  @Singleton
  MessageSource messageSource() {
    return new ResourceBundleMessageSource("i18n.messages");
  }

  @Singleton
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @TraceLog
  @Singleton
  <T> GenericJwtClaimsValidator<T> vertxUserClaimsValidator() {
    return (claims, request) -> {
      log.trace("START...");

      JWTClaimsSet jwt = JWTClaimsSetUtils.jwtClaimsSetFromClaims(claims);
      String username = jwt.getSubject();
      var user = vertx.getData(username, VertxConfig.VertxUser.class).join();

      if (user.isEmpty()) {
        log.error("Vertx user not found! {}", username);
        return false;
      }

      log.debug("Token validated...{}", username);
      return true;
    };
  }
}
