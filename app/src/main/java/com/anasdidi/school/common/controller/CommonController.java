/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.controller;

import io.micronaut.http.HttpRequest;
import java.util.Map;

public abstract class CommonController {

  public final Map<String, String> parseParameters(HttpRequest<?> request) {
    return request.getParameters().asMap(String.class, String.class);
  }
}
