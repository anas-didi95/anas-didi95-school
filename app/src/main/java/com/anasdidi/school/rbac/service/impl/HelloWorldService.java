/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.dto.HelloWorldReqDTO;
import com.anasdidi.school.rbac.dto.HelloWorldResDTO;
import com.anasdidi.school.rbac.service.RbacService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_HELLO_WORLD)
@Slf4j
class HelloWorldService extends RbacService<HelloWorldReqDTO, HelloWorldResDTO> {

  @Override
  protected HelloWorldResDTO execute(HelloWorldReqDTO in) {
    log.trace("START...");

    log.debug("Greeting returned...{}", in.name());
    return HelloWorldResDTO.builder()
        .greeting("Hi %s, from RBAC module".formatted(in.name()))
        .build();
  }
}
