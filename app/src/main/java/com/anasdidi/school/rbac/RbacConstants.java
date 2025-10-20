/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac;

import com.anasdidi.school.common.CommonConstants.CommonEvent;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.rbac.dto.CheckAccessReqDTO;
import com.anasdidi.school.rbac.dto.CheckAccessResDTO;
import com.anasdidi.school.rbac.dto.ListAccessReqDTO;
import com.anasdidi.school.rbac.dto.ListAccessResDTO;
import com.anasdidi.school.rbac.dto.ListRoleReqDTO;
import com.anasdidi.school.rbac.dto.ListRoleResDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RbacConstants {

  @UtilityClass
  public static class Event {
    public static final String RBAC_LIST_ACCESS = "RBAC_LST_ACCS";
    public static final String RBAC_CHECK_ACCESS = "RBAC_CHK_ACCS";
    public static final String RBAC_LIST_ROLE = "RBAC_LST_ROLE";
  }

  @Getter
  @RequiredArgsConstructor
  public enum EventEnum implements CommonEvent {
    RBAC_LIST_ACCESS(Event.RBAC_LIST_ACCESS, ListAccessReqDTO.class, ListAccessResDTO.class),
    RBAC_CHECK_ACCESS(Event.RBAC_CHECK_ACCESS, CheckAccessReqDTO.class, CheckAccessResDTO.class),
    RBAC_LIST_ROLE(Event.RBAC_LIST_ROLE, ListRoleReqDTO.class, ListRoleResDTO.class);

    private final String address;
    private final Class<? extends CommonReqDTO> reqClass;
    private final Class<? extends CommonResDTO> resClass;
  }

  public static final String BASE_URL = "/rbac";
  public static final String CONNECTION_NAME = "CN-RBAC";

  public static final String FULL_ACCESS = "FULL_ACCS";
}
