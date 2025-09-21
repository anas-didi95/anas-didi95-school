/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import io.micronaut.context.MessageSource;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.i18n.ResourceBundleMessageSource;
import jakarta.inject.Singleton;

@Factory
public class CommonConfig {

  @Singleton
  MessageSource messageSource() {
    return new ResourceBundleMessageSource("i18n.messages");
  }
}
