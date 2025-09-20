/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.service;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;

public abstract class CommonService<A extends CommonReqDTO, B extends CommonResDTO> {

  protected abstract B execute(A in);

  @TraceLog
  public B process(A in) {
    return execute(in);
  }
}
