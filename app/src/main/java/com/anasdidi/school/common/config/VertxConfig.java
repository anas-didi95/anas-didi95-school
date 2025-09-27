/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import io.micronaut.context.annotation.Context;
import io.vertx.core.Vertx;
import io.vertx.core.json.jackson.DatabindCodec;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

@Context
@Slf4j
public class VertxConfig {

  private final Vertx vertx;

  VertxConfig() {
    this.vertx = Vertx.vertx();
    DatabindCodec.mapper().findAndRegisterModules();
    log.info("Vertx started...");
  }

  public Vertx vertx() {
    return this.vertx;
  }

  @PreDestroy
  void preDestroy() {
    vertx
        .close()
        .onComplete(
            event -> log.info("Vertx closed..."),
            error -> log.error("Vertx closing failed!", error));
  }
}
