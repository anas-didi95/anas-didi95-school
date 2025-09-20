/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service.impl;

import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.dto.HelloWorldReqDTO;
import com.anasdidi.school.user.dto.HelloWorldResDTO;
import com.anasdidi.school.user.service.UserService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named(UserConstants.Action.USER_HELLO_WORLD)
class HelloWorld extends UserService<HelloWorldReqDTO, HelloWorldResDTO> {

  @Override
  protected HelloWorldResDTO execute(HelloWorldReqDTO in) {
    return HelloWorldResDTO.builder().greeting("Hi, %s".formatted(in.name())).build();
  }
}
