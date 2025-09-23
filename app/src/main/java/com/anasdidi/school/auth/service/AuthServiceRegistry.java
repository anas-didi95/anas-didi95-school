/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service;

import com.anasdidi.school.auth.AuthConstants.ServiceEnum;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import jakarta.inject.Singleton;
import java.util.Map;
import lombok.AllArgsConstructor;

@Singleton
@AllArgsConstructor
public class AuthServiceRegistry {

  private final Map<String, AuthService<?, ?>> serviceMap;

  @SuppressWarnings("unchecked")
  public final <A extends CommonReqDTO, B extends CommonResDTO> AuthService<A, B> get(
      ServiceEnum e) {
    return (AuthService<A, B>) get(e.action, e.reqClass, e.resClass);
  }

  @SuppressWarnings("unchecked")
  private final <A extends CommonReqDTO, B extends CommonResDTO> AuthService<A, B> get(
      String action, Class<A> reqClass, Class<B> resClass) {
    return (AuthService<A, B>) serviceMap.get(action);
  }
}
