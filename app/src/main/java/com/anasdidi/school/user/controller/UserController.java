/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.controller;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.user.dto.AddUserReqDTO;
import com.anasdidi.school.user.dto.AddUserResDTO;
import com.anasdidi.school.user.dto.SearchUserResDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;

public abstract class UserController {

  @TraceLog
  protected abstract HttpResponse<AddUserResDTO> addUser(AddUserReqDTO body);

  @TraceLog
  protected abstract HttpResponse<SearchUserResDTO> searchUser(HttpRequest<Void> request);
}
