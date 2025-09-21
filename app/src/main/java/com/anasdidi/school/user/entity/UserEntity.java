/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.entity;

import com.anasdidi.school.common.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "T_USER")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserEntity extends CommonEntity {

  @Column(name = "USERNAME")
  private String username;

  @Column(name = "PWD")
  private String password;

  @Column(name = "NM")
  private String name;

  @Column(name = "LAST_SIGNIN_DT")
  private OffsetDateTime lastSigninDate;
}
