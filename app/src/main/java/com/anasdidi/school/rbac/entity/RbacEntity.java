/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.entity;

import com.anasdidi.school.common.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "T_RBAC")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RbacEntity extends CommonEntity {

  @Column(name = "ROLE")
  private String role;

  @Column(name = "ACC_LS")
  private String accessList;
}
