/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.entity;

import io.micronaut.data.annotation.DateUpdated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;

@Entity
@Table(name = "T_AUTH")
@Data
public class AuthEntity {

  @Id
  @Column(name = "ID")
  private UUID id;

  @Column(name = "UPDATE_BY")
  private String updateBy;

  @DateUpdated
  @Column(name = "UPDATE_DT")
  private OffsetDateTime updateDate;

  @Column(name = "USERNAME")
  private String username;

  @Column(name = "REFRESH_TOKEN")
  private String refreshToken;
}
