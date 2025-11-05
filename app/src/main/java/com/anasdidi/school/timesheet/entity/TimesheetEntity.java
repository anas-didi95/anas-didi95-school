/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.entity;

import com.anasdidi.school.common.entity.AuditEntityListener;
import com.anasdidi.school.common.entity.UpdateAudit;
import com.anasdidi.school.timesheet.TimesheetConstants.TimesheetTypeEnum;
import io.micronaut.data.annotation.DateUpdated;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Entity
@Table(name = "T_TIMESHEET")
@Data
@EntityListeners(AuditEntityListener.class)
public class TimesheetEntity implements UpdateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "ID")
  private UUID id;

  @Column(name = "UPDATE_BY")
  private String updateBy;

  @DateUpdated
  @Column(name = "UPDATE_DT")
  private OffsetDateTime updateDate;

  @Column(name = "TYP")
  @Convert(converter = TimesheetTypeEnum.Converter.class)
  private TimesheetTypeEnum type;
}
