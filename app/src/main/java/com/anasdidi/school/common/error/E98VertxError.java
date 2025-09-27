/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;
import io.vertx.core.json.JsonArray;

public final class E98VertxError extends BaseError {

  @SuppressWarnings("unchecked")
  public E98VertxError(CommonConstants.Error error, JsonArray variables) {
    super(error, (String[]) variables.getList().toArray(String[]::new));
  }
}
