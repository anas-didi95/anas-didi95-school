/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import com.anasdidi.school.user.dto.SearchUserReqDTO;
import com.anasdidi.school.user.dto.SearchUserResDTO;
import com.anasdidi.school.user.dto.UpdateUserReqDTO;
import com.anasdidi.school.user.dto.UpdateUserResDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConstants {

  @UtilityClass
  public static class Action {
    public static final String USER_ADD_USER = "USER_ADD_USER";
    public static final String USER_SEARCH_USER = "USER_SEARCH_USER";
    public static final String USER_GET_USER = "USER_GET_USER";
    public static final String USER_UPDATE_USER = "USER_UPDATE_USER";
  }

  public enum ServiceEnum {
    USER_ADD_USER(Action.USER_ADD_USER, AddUserReqDTO.class, AddUserResDTO.class),
    USER_SEARCH_USER(Action.USER_SEARCH_USER, SearchUserReqDTO.class, SearchUserResDTO.class),
    USER_GET_USER(Action.USER_GET_USER, GetUserReqDTO.class, GetUserResDTO.class),
    USER_UPDATE_USER(Action.USER_UPDATE_USER, UpdateUserReqDTO.class, UpdateUserResDTO.class);

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
