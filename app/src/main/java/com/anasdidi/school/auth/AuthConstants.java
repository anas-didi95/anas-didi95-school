/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth;

import com.anasdidi.school.auth.dto.HelloWorldReqDTO;
import com.anasdidi.school.auth.dto.HelloWorldResDTO;
import com.anasdidi.school.auth.dto.RefreshTokenReqDTO;
import com.anasdidi.school.auth.dto.RefreshTokenResDTO;
import com.anasdidi.school.auth.dto.SignInReqDTO;
import com.anasdidi.school.auth.dto.SignInResDTO;
import com.anasdidi.school.auth.dto.SignOutReqDTO;
import com.anasdidi.school.auth.dto.SignOutResDTO;
import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import lombok.Getter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthConstants {

  @UtilityClass
  public static class Event {
    public static final String AUTH_HELLO_WORLD = "AUTH_HELLO_WORLD";
    public static final String AUTH_SIGN_IN = "AUTH_SIGN_IN";
    public static final String AUTH_REFRESH_TOKEN = "AUTH_REFRESH_TOKEN";
    public static final String AUTH_SIGN_OUT = "AUTH_SIGN_OUT";
  }

  @Getter
  public enum EventEnum implements CommonEvent {
    AUTH_HELLO_WORLD(Event.AUTH_HELLO_WORLD, HelloWorldReqDTO.class, HelloWorldResDTO.class),
    AUTH_SIGN_IN(Event.AUTH_SIGN_IN, SignInReqDTO.class, SignInResDTO.class),
    AUTH_REFRESH_TOKEN(
        Event.AUTH_REFRESH_TOKEN, RefreshTokenReqDTO.class, RefreshTokenResDTO.class),
    AUTH_SIGN_OUT(Event.AUTH_SIGN_OUT, SignOutReqDTO.class, SignOutResDTO.class);

    private final String address;
    private final Class<? extends CommonReqDTO> reqClass;
    private final Class<? extends CommonResDTO> resClass;

    EventEnum(
        String address,
        Class<? extends CommonReqDTO> reqClass,
        Class<? extends CommonResDTO> resClass) {
      this.address = address;
      this.reqClass = reqClass;
      this.resClass = resClass;
    }
  }

  public static final String BASE_URL = "/auth";
  // public static final String CONNECTION_NAME = "CN-USER";
}
