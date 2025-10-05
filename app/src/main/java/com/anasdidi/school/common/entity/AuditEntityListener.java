/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.entity;

import com.anasdidi.school.common.CommonConstants;
import jakarta.persistence.PrePersist;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

@Slf4j
public class AuditEntityListener {

  @PrePersist
  void prePresistCreateUpdateAudit(Object entity) {
    log.trace("START...");

    var user =
        Optional.ofNullable(MDC.get(CommonConstants.MDC_USERNAME))
            .orElse(CommonConstants.SYSTEM_USER);

    if (entity instanceof CreateAudit e) {
      var alreadyHas = StringUtils.isNotBlank(e.getCreateBy());
      if (!alreadyHas) {
        e.setCreateBy(user);
      }
      log.trace("alreadyHas={}, createBy={}", alreadyHas, e.getCreateBy());
    }

    if (entity instanceof UpdateAudit e) {
      e.setUpdateBy(user);
      log.trace("updateBy={}", e.getUpdateBy());
    }
  }
}
