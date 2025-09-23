/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.error;

import com.anasdidi.school.common.CommonConstants;
import java.util.Arrays;

public final class E03RecordNotFoundError extends BaseError {

  public E03RecordNotFoundError(String record) {
    super(
        CommonConstants.Error.E03_RECORD_NOT_FOUND_ERR,
        Arrays.asList(record).toArray(String[]::new));
  }
}
