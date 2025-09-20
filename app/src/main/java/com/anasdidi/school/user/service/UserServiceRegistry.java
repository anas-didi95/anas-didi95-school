/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.user.UserConstants.ServiceEnum;
import jakarta.inject.Singleton;
import java.util.Map;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class UserServiceRegistry {

  private final Map<String, UserService<?, ?>> serviceMap;

  @SuppressWarnings("unchecked")
  public final <A extends CommonReqDTO, B extends CommonResDTO> UserService<A, B> get(
      ServiceEnum e) {
    return (UserService<A, B>) get(e.action, e.reqClass, e.resClass);
  }

  @SuppressWarnings("unchecked")
  private final <A extends CommonReqDTO, B extends CommonResDTO> UserService<A, B> get(
      String action, Class<A> reqClass, Class<B> resClass) {
    return (UserService<A, B>) serviceMap.get(action);
  }
}
