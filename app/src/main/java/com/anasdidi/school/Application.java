/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.bridge.SLF4JBridgeHandler;

@OpenAPIDefinition(
    info =
        @Info(
            title = "anas-didi95-school",
            version = "v0.1.0",
            contact =
                @Contact(email = "anas.didi95@tutamail", name = "Anas Juwaidi Bin Mohd Jeffry")))
public class Application {

  public static void main(String[] args) {
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    Micronaut.run(Application.class, args);
  }
}
