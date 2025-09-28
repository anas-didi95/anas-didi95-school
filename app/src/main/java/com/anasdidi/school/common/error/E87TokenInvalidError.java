/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;

public final class E87TokenInvalidError extends BaseError {

  public E87TokenInvalidError() {
    super(CommonConstants.Error.E87_TOKEN_INVALID_ERR, null);
  }
}
