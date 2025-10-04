/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user;

import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.DeleteUserReqDTO;
import com.anasdidi.school.user.dto.DeleteUserResDTO;
import com.anasdidi.school.user.dto.GetUserReqDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import com.anasdidi.school.user.dto.SearchUserReqDTO;
import com.anasdidi.school.user.dto.SearchUserResDTO;
import com.anasdidi.school.user.dto.UpdateUserReqDTO;
import com.anasdidi.school.user.dto.UpdateUserResDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserConstants {

  @UtilityClass
  public static class Event {
    public static final String USER_ADD_USER = "USER_ADD_USER";
    public static final String USER_SEARCH_USER = "USER_SEARCH_USER";
    public static final String USER_GET_USER = "USER_GET_USER";
    public static final String USER_UPDATE_USER = "USER_UPDATE_USER";
    public static final String USER_DELETE_USER = "USER_DELETE_USER";
  }

  @Getter
  @RequiredArgsConstructor
  public enum EventEnum implements CommonEvent {
    USER_ADD_USER(Event.USER_ADD_USER, AddUserReqDTO.class, AddUserResDTO.class),
    USER_SEARCH_USER(Event.USER_SEARCH_USER, SearchUserReqDTO.class, SearchUserResDTO.class),
    USER_GET_USER(Event.USER_GET_USER, GetUserReqDTO.class, GetUserResDTO.class),
    USER_UPDATE_USER(Event.USER_UPDATE_USER, UpdateUserReqDTO.class, UpdateUserResDTO.class),
    USER_DELETE_USER(Event.USER_DELETE_USER, DeleteUserReqDTO.class, DeleteUserResDTO.class);

    private final String address;
    private final Class<? extends CommonReqDTO> reqClass;
    private final Class<? extends CommonResDTO> resClass;
  }

  public static final String BASE_URL = "/user";
  public static final String CONNECTION_NAME = "CN-USER";
}
