/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth;

import com.anasdidi.school.common.config.VertxConfig;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.generator.AccessRefreshTokenGenerator;
import io.micronaut.security.token.render.AccessRefreshToken;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.password.PasswordEncoder;

@UtilityClass
public class AuthUtils {

  public static final AccessRefreshToken prepareToken(
      VertxConfig vertx,
      AccessRefreshTokenGenerator generator,
      PasswordEncoder passwordEncoder,
      Authentication authentication) {
    var name = authentication.getName();
    var refreshToken = name + UUID.randomUUID();

    vertx.putData(name, VertxConfig.VertxUser.builder().refreshToken(refreshToken).build());
    return generator.generate(passwordEncoder.encode(refreshToken), authentication).get();
  }
}
