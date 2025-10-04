/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;

public final class E86AccessDeniedError extends BaseError {

  public E86AccessDeniedError() {
    super(CommonConstants.Error.E86_ACCESS_DENIED_ERR, null);
  }
}
