/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;
import java.util.Arrays;

public final class E02RecordAlreadyExistsError extends BaseError {

  public E02RecordAlreadyExistsError(String field) {
    super(
        CommonConstants.Error.E02_RECORD_ALREADY_EXIST_ERR,
        Arrays.asList(field).toArray(String[]::new));
  }
}
