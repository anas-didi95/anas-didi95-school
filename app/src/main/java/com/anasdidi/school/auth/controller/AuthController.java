/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.controller;

import com.anasdidi.school.auth.dto.RefreshTokenReqDTO;
import com.anasdidi.school.auth.dto.RefreshTokenResDTO;
import com.anasdidi.school.auth.dto.SignInReqDTO;
import com.anasdidi.school.auth.dto.SignInResDTO;
import com.anasdidi.school.auth.dto.SignOutResDTO;
import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.controller.CommonController;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.security.authentication.Authentication;
import io.swagger.v3.oas.annotations.Operation;

public abstract class AuthController extends CommonController {

  private static final String OPENAPI_TAG = "Auth API";

  @TraceLog
  @Operation(summary = "Sign In", tags = OPENAPI_TAG)
  protected abstract HttpResponse<SignInResDTO> signIn(HttpRequest<?> request, SignInReqDTO body);

  @TraceLog
  @Operation(summary = "Refresh Token", tags = OPENAPI_TAG)
  protected abstract HttpResponse<RefreshTokenResDTO> refreshToken(
      HttpRequest<?> request, RefreshTokenReqDTO body);

  @TraceLog
  @Operation(summary = "Sign Out", tags = OPENAPI_TAG)
  protected abstract HttpResponse<SignOutResDTO> signOut(
      HttpRequest<?> request, Authentication authentication);
}
