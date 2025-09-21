/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;
import java.util.Arrays;
import java.util.Map;

public final class E01ValidationError extends BaseError {

  public E01ValidationError(Map<String, Object> paramMap) {
    super(
        CommonConstants.Error.E01_VALIDATION_ERR,
        Arrays.asList(parseParamMap(paramMap)).toArray(String[]::new));
  }
}
