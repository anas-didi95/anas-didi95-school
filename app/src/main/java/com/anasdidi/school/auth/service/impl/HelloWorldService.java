/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.service.impl;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.dto.HelloWorldReqDTO;
import com.anasdidi.school.auth.dto.HelloWorldResDTO;
import com.anasdidi.school.auth.service.AuthService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named(AuthConstants.Action.AUTH_HELLO_WORLD)
class HelloWorldService extends AuthService<HelloWorldReqDTO, HelloWorldResDTO> {

  @Override
  protected HelloWorldResDTO execute(HelloWorldReqDTO in) {
    return HelloWorldResDTO.builder().greeting("Hi from Auth, %s".formatted(in.name())).build();
  }
}
