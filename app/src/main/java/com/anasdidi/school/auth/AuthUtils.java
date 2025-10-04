/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth;

import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.user.dto.model.UserDTO;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.generator.AccessRefreshTokenGenerator;
import io.micronaut.security.token.render.AccessRefreshToken;
import io.vertx.core.json.JsonObject;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

@UtilityClass
@Slf4j
public class AuthUtils {

  public static final AccessRefreshToken prepareToken(
      Long timerSeconds,
      VertxConfig vertx,
      AccessRefreshTokenGenerator generator,
      PasswordEncoder passwordEncoder,
      UserDTO user) {
    var name = user.username();
    var refreshToken = user.id().toString() + UUID.randomUUID();
    var authentication =
        Authentication.build(
            name,
            user.roleList(),
            new JsonObject()
                .put("userId", user.id())
                .put("lastSignInDate", System.currentTimeMillis())
                .getMap());

    vertx
        .stopTimer(name)
        .thenAccept(
            t -> {
              CompletableFuture.allOf(
                      vertx.putData(
                          name,
                          VertxConfig.VertxUser.builder()
                              .refreshToken(refreshToken)
                              .userId(user.id())
                              .build()),
                      vertx.startTimer(
                          timerSeconds,
                          name,
                          event -> vertx.clearData(name, VertxConfig.VertxUser.class)))
                  .exceptionally(
                      e -> {
                        log.error("Fail to store token or start timer! {}", e);
                        return null;
                      });
            });

    return generator.generate(passwordEncoder.encode(refreshToken), authentication).get();
  }
}
