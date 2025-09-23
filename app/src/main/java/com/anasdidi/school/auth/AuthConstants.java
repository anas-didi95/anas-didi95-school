/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth;

import com.anasdidi.school.auth.dto.HelloWorldReqDTO;
import com.anasdidi.school.auth.dto.HelloWorldResDTO;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthConstants {

  @UtilityClass
  public static class Action {
    public static final String AUTH_HELLO_WORLD = "AUTH_HELLO_WORLD";
  }

  public enum ServiceEnum {
    AUTH_HELLO_WORLD(Action.AUTH_HELLO_WORLD, HelloWorldReqDTO.class, HelloWorldResDTO.class);

    public final String action;
    public final Class<? extends CommonReqDTO> reqClass;
    public final Class<? extends CommonResDTO> resClass;

    ServiceEnum(
        String action,
        Class<? extends CommonReqDTO> reqClass,
        Class<? extends CommonResDTO> resClass) {
      this.action = action;
      this.reqClass = reqClass;
      this.resClass = resClass;
    }
  }

  public static final String BASE_URL = "/auth";
  // public static final String CONNECTION_NAME = "CN-USER";
}
