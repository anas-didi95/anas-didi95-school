/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.repository;

import com.anasdidi.school.timesheet.TimesheetConstants;
import com.anasdidi.school.timesheet.entity.TimesheetEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;
import java.util.UUID;

@Repository(TimesheetConstants.CONNECTION_NAME)
public interface TimesheetRepository
    extends PageableRepository<TimesheetEntity, UUID>, JpaSpecificationExecutor<TimesheetEntity> {}
