/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac;

import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.rbac.dto.CheckAccessReqDTO;
import com.anasdidi.school.rbac.dto.CheckAccessResDTO;
import com.anasdidi.school.rbac.dto.HelloWorldReqDTO;
import com.anasdidi.school.rbac.dto.HelloWorldResDTO;
import com.anasdidi.school.rbac.dto.ListAccessReqDTO;
import com.anasdidi.school.rbac.dto.ListAccessResDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RbacConstants {

  @UtilityClass
  public static class Event {
    public static final String RBAC_HELLO_WORLD = "RBAC_HELLO_WORLD";
    public static final String RBAC_LIST_ACCESS = "RBAC_LST_ACCS";
    public static final String RBAC_CHECK_ACCESS = "RBAC_CHK_ACCS";
  }

  @Getter
  @RequiredArgsConstructor
  public enum EventEnum implements CommonEvent {
    RBAC_HELLO_WORLD(Event.RBAC_HELLO_WORLD, HelloWorldReqDTO.class, HelloWorldResDTO.class),
    RBAC_LIST_ACCESS(Event.RBAC_LIST_ACCESS, ListAccessReqDTO.class, ListAccessResDTO.class),
    RBAC_CHECK_ACCESS(Event.RBAC_CHECK_ACCESS, CheckAccessReqDTO.class, CheckAccessResDTO.class);

    private final String address;
    private final Class<? extends CommonReqDTO> reqClass;
    private final Class<? extends CommonResDTO> resClass;
  }

  public static final String BASE_URL = "/rbac";
  public static final String CONNECTION_NAME = "CN-RBAC";
}
