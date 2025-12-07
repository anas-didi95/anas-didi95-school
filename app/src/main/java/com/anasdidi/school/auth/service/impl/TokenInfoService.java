/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.dto.TokenInfoReqDTO;
import com.anasdidi.school.auth.dto.TokenInfoResDTO;
import com.anasdidi.school.auth.service.AuthService;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.endpoints.introspection.DefaultIntrospectionProcessor;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(AuthConstants.Event.AUTH_TOKEN_INFO)
@RequiredArgsConstructor
@Slf4j
class TokenInfoService extends AuthService<TokenInfoReqDTO, TokenInfoResDTO> {

  private final DefaultIntrospectionProcessor<HttpRequest<?>> processor;

  @Override
  protected TokenInfoResDTO execute(TokenInfoReqDTO in) {
    log.trace("START...");

    var result = processor.createIntrospectionResponse(in.authentication(), in.request());

    log.debug("Token info created...{}", result);
    return TokenInfoResDTO.builder().result(result).build();
  }
}
