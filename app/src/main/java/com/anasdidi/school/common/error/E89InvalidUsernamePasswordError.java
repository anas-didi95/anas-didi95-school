/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;

public final class E89InvalidUsernamePasswordError extends BaseError {

  public E89InvalidUsernamePasswordError() {
    super(CommonConstants.Error.E89_INVALID_USERNM_PASSWD_ERR, null);
  }
}
