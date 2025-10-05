/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.rbac.RbacConstants.EventEnum;
import jakarta.inject.Singleton;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class RbacServiceRegistry {

  private final Map<String, RbacService<?, ?>> serviceMap;

  @SuppressWarnings("unchecked")
  public final <A extends CommonReqDTO, B extends CommonResDTO> RbacService<A, B> get(EventEnum e) {
    return (RbacService<A, B>) get(e.getAddress(), e.getReqClass(), e.getResClass());
  }

  @SuppressWarnings("unchecked")
  private final <A extends CommonReqDTO, B extends CommonResDTO> RbacService<A, B> get(
      String address, Class<A> reqClass, Class<B> resClass) {
    return (RbacService<A, B>) serviceMap.get(address);
  }
}
