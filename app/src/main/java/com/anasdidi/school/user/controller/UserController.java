/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.GetUserResDTO;
import com.anasdidi.school.user.dto.SearchUserResDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import java.util.UUID;

public abstract class UserController extends CommonController {

  @TraceLog
  protected abstract HttpResponse<AddUserResDTO> addUser(HttpRequest<AddUserReqDTO> request);

  @TraceLog
  protected abstract HttpResponse<SearchUserResDTO> searchUser(HttpRequest<Void> request);

  @TraceLog
  protected abstract HttpResponse<GetUserResDTO> getUser(HttpRequest<Void> request, UUID userId);
}
