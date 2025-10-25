/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.service;

import com.anasdidi.school.common.config.TraceLog;
import com.anasdidi.school.common.config.VertxConfig;
import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.common.error.E86AccessDeniedError;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.dto.CheckAccessReqDTO;
import com.anasdidi.school.rbac.dto.CheckAccessResDTO;
import io.micronaut.context.BeanContext;
import io.micronaut.security.utils.SecurityService;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.Optional;

public abstract class CommonService<A extends CommonReqDTO, B extends CommonResDTO> {

  protected abstract B execute(A in);

  @Inject private BeanContext beanContext;
  @Inject private SecurityService securityService;
  @Inject private VertxConfig vertx;

  public B process(@Valid A in) {
    return process(in, true);
  }

  @TraceLog
  public B process(@Valid A in, boolean checkAccess) {
    if (checkAccess && securityService.isAuthenticated()) {
      var access = beanContext.getBeanDefinition(this.getClass()).getBeanName().get();
      var roles =
          Optional.ofNullable(securityService.getAuthentication().get().getRoles())
              .orElse(Collections.emptyList());

      var result =
          vertx
              .requestEvent(
                  RbacConstants.EventEnum.RBAC_CHECK_ACCESS,
                  JsonObject.mapFrom(
                      CheckAccessReqDTO.builder().roleList(roles).access(access).build()))
              .thenApply(reply -> reply.mapTo(CheckAccessResDTO.class))
              .join();
      if (!result.isValid()) {
        throw new E86AccessDeniedError();
      }
    }

    return execute(in);
  }
}
