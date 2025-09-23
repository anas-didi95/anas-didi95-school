/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.common.service.CommonService;

public abstract class AuthService<A extends CommonReqDTO, B extends CommonResDTO>
    extends CommonService<A, B> {}
