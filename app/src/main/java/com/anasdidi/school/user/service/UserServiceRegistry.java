/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.user.UserConstants.EventEnum;
import jakarta.inject.Singleton;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class UserServiceRegistry {

  private final Map<String, UserService<?, ?>> serviceMap;

  @SuppressWarnings("unchecked")
  public final <A extends CommonReqDTO, B extends CommonResDTO> UserService<A, B> get(EventEnum e) {
    return (UserService<A, B>) get(e.getAddress(), e.getReqClass(), e.getResClass());
  }

  @SuppressWarnings("unchecked")
  private final <A extends CommonReqDTO, B extends CommonResDTO> UserService<A, B> get(
      String address, Class<A> reqClass, Class<B> resClass) {
    return (UserService<A, B>) serviceMap.get(address);
  }
}
