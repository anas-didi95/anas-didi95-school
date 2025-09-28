/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonConstants {

  public enum Error {
    E01_VALIDATION_ERR,
    E02_RECORD_ALREADY_EXIST_ERR,
    E03_RECORD_NOT_FOUND_ERR,
    E87_TOKEN_INVALID_ERR,
    E88_USER_DISABLED_ERR,
    E89_INVALID_USERNM_PASSWD_ERR,
    E98_VERTX_ERR,
    E99_UNEXPECTED_ERR;

    public final String code() {
      return this.name().split("_")[0];
    }
  }

  public interface CommonEvent {
    String getAddress();

    Class<? extends CommonReqDTO> getReqClass();

    Class<? extends CommonResDTO> getResClass();
  }

  public static final String V1_URL = "/api/v1";

  public static final String MDC_TRACEID = "TraceId";
  public static final String MDC_CLASSMETHOD = "ClassMethod";
}
