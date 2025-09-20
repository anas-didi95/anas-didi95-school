/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;

public abstract class UserService<A extends CommonReqDTO, B extends CommonResDTO> {

  protected abstract B execute(A in);

  public final B process(A in) {
    return execute(in);
  }
}
