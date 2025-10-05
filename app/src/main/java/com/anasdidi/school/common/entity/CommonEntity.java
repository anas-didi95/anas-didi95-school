/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.entity;

import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.DateUpdated;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@MappedSuperclass
@Data
@EntityListeners(AuditEntityListener.class)
public abstract class CommonEntity implements CreateAudit, UpdateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "ID")
  private UUID id;

  @Column(name = "IS_DEL")
  private Boolean isDeleted;

  @Version
  @Column(name = "VER")
  private Integer version;

  @Column(name = "CREATE_BY")
  private String createBy;

  @DateCreated
  @Column(name = "CREATE_DT")
  private OffsetDateTime createDate;

  @Column(name = "UPDATE_BY")
  private String updateBy;

  @DateUpdated
  @Column(name = "UPDATE_DT")
  private OffsetDateTime updateDate;
}
