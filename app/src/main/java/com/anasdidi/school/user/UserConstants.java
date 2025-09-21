/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.HelloWorldReqDTO;
import com.anasdidi.school.user.dto.HelloWorldResDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConstants {

  @UtilityClass
  public static class Action {
    public static final String USER_HELLO_WORLD = "USER_HELLO_WORLD";
    public static final String USER_ADD_USER = "USER_ADD_USER";
  }

  public enum ServiceEnum {
    USER_HELLO_WORLD(Action.USER_HELLO_WORLD, HelloWorldReqDTO.class, HelloWorldResDTO.class),
    USER_ADD_USER(Action.USER_ADD_USER, AddUserReqDTO.class, AddUserResDTO.class);

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

  public static final String BASE_URL = "/user";
  public static final String CONNECTION_NAME = "CN-USER";
}
