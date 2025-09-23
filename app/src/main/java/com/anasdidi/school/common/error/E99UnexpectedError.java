/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;
import java.util.Arrays;
import java.util.Map;

public final class E99UnexpectedError extends BaseError {

  public E99UnexpectedError(Map<String, Object> paramMap) {
    super(
        CommonConstants.Error.E99_UNEXPECTED_ERR,
        Arrays.asList(parseParamMap(paramMap)).toArray(String[]::new));
  }
}
