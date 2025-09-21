/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

  public enum Error {
    E01_VALIDATION_ERR,
    E02_RECORD_ALREADY_EXIST_ERR,
    E03_RECORD_NOT_FOUND_ERR,
    E99_UNEXPECTED_ERR;

    public final String code() {
      return this.name().split("_")[0];
    }
  }

  public static final String V1_URL = "/api/v1";
}
