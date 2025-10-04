/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.config.VertxConfig.VertxAccess;
import com.anasdidi.school.common.error.E03RecordNotFoundError;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.dto.ListAccessReqDTO;
import com.anasdidi.school.rbac.dto.ListAccessResDTO;
import com.anasdidi.school.rbac.service.RbacService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_LIST_ACCESS)
@RequiredArgsConstructor
@Slf4j
class ListAccessService extends RbacService<ListAccessReqDTO, ListAccessResDTO> {

  private final VertxConfig vertx;

  @Override
  protected ListAccessResDTO execute(ListAccessReqDTO in) {
    log.trace("START...");

    var access = vertx.getData(VertxConfig.ACCESS_SET_KEY, VertxAccess.class).join();
    if (access.isEmpty()) {
      log.error("Access set not found!");
      throw new E03RecordNotFoundError("Access Set");
    }

    var accessList = access.get().accessSet().stream().sorted().toList();

    log.debug("List completed...");
    return ListAccessResDTO.builder().resultList(accessList).build();
  }
}
