/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.controller.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.AuthConstants.EventEnum;
import com.anasdidi.school.auth.controller.AuthController;
import com.anasdidi.school.auth.dto.RefreshTokenReqDTO;
import com.anasdidi.school.auth.dto.RefreshTokenResDTO;
import com.anasdidi.school.auth.dto.SignInReqDTO;
import com.anasdidi.school.auth.dto.SignInResDTO;
import com.anasdidi.school.auth.dto.SignOutReqDTO;
import com.anasdidi.school.auth.dto.SignOutResDTO;
import com.anasdidi.school.auth.dto.TokenInfoReqDTO;
import com.anasdidi.school.auth.dto.TokenInfoResDTO;
import com.anasdidi.school.auth.service.AuthServiceRegistry;
import com.anasdidi.school.common.CommonConstants;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.authentication.Authentication;
import lombok.RequiredArgsConstructor;

@Controller(CommonConstants.V1_URL + AuthConstants.BASE_URL)
@RequiredArgsConstructor
class AuthControllerV1 extends AuthController {

  private final AuthServiceRegistry registry;

  @Override
  @Post("/sign-in")
  protected HttpResponse<SignInResDTO> signIn(HttpRequest<?> request, @Body SignInReqDTO body) {
    return HttpResponse.ok(
        (SignInResDTO) registry.get(EventEnum.AUTH_SIGN_IN).process(body, false));
  }

  @Override
  @Post("/refresh-token")
  protected HttpResponse<RefreshTokenResDTO> refreshToken(
      HttpRequest<?> request, @Body RefreshTokenReqDTO body) {
    return HttpResponse.ok(
        (RefreshTokenResDTO) registry.get(EventEnum.AUTH_REFRESH_TOKEN).process(body, false));
  }

  @Override
  @Get("/sign-out")
  protected HttpResponse<SignOutResDTO> signOut(
      HttpRequest<?> request, @Nullable Authentication authentication) {
    var body = SignOutReqDTO.builder().username(authentication.getName()).build();
    return HttpResponse.ok(
        (SignOutResDTO) registry.get(EventEnum.AUTH_SIGN_OUT).process(body, false));
  }

  @Override
  @Get("/token-info")
  protected HttpResponse<TokenInfoResDTO> token(
      HttpRequest<?> request, @Nullable Authentication authentication) {
    var body = TokenInfoReqDTO.builder().authentication(authentication).request(request).build();
    return HttpResponse.ok((TokenInfoResDTO) registry.get(EventEnum.AUTH_TOKEN_INFO).process(body));
  }
}
