/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Factory
@RequiredArgsConstructor
@Slf4j
public class CommonConfig {

  @Singleton
  MessageSource messageSource() {
    return new ResourceBundleMessageSource("i18n.messages");
  }

  @Singleton
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
