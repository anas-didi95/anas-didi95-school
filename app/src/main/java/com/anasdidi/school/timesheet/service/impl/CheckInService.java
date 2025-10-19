/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.service.impl;

import com.anasdidi.school.common.error.E86AccessDeniedError;
import com.anasdidi.school.timesheet.TimesheetConstants;
import com.anasdidi.school.timesheet.TimesheetConstants.TimesheetTypeEnum;
import com.anasdidi.school.timesheet.dto.CheckInReqDTO;
import com.anasdidi.school.timesheet.dto.CheckInResDTO;
import com.anasdidi.school.timesheet.entity.TimesheetEntity;
import com.anasdidi.school.timesheet.entity.TimesheetEntity_;
import com.anasdidi.school.timesheet.repository.TimesheetRepository;
import com.anasdidi.school.timesheet.service.TimesheetService;
import io.micronaut.security.utils.SecurityService;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(TimesheetConstants.Event.TSHT_CHECK_IN)
@RequiredArgsConstructor
@Slf4j
class CheckInService extends TimesheetService<CheckInReqDTO, CheckInResDTO> {

  private final SecurityService securityService;
  private final TimesheetRepository timesheetRepository;

  @Override
  protected CheckInResDTO execute(CheckInReqDTO in) {
    log.trace("START...");

    var username =
        securityService
            .username()
            .orElseThrow(
                () -> {
                  log.error("User not authenticated!");
                  return new E86AccessDeniedError();
                });
    var result =
        timesheetRepository.findAll(
            (root, query, criteriaBuilder) -> {
              query.orderBy(criteriaBuilder.asc(root.get(TimesheetEntity_.UPDATE_DATE)));
              return criteriaBuilder.and(
                  criteriaBuilder.equal(root.get(TimesheetEntity_.UPDATE_BY), username),
                  criteriaBuilder.greaterThanOrEqualTo(
                      root.get(TimesheetEntity_.UPDATE_DATE), criteriaBuilder.currentDate()),
                  criteriaBuilder.equal(
                      root.get(TimesheetEntity_.TYPE), TimesheetTypeEnum.CHECK_IN));
            });

    var out = CheckInResDTO.builder();
    if (!result.isEmpty()) {
      out.lastDateTime(result.get(0).getUpdateDate());
    } else {
      var timesheet = new TimesheetEntity();
      timesheet.setType(TimesheetTypeEnum.CHECK_IN);
      var entity = timesheetRepository.save(timesheet);
      out.lastDateTime(entity.getUpdateDate());
    }

    var out2 = out.build();
    log.info("Checked in {}...{}", username, out2.lastDateTime());
    return out2;
  }
}
