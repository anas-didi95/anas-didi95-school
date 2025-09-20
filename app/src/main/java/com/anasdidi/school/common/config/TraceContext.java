/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.config;

import io.micronaut.runtime.http.scope.RequestScope;
import lombok.Data;

@RequestScope
@Data
public class TraceContext {

  private String traceId;
  private String controller;
  private String controllerParam;
  private String classMethod;
}
