/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.entity;

import com.anasdidi.school.common.converter.ListStringConverter;
import com.anasdidi.school.common.entity.CommonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "T_RBAC")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RbacEntity extends CommonEntity {

  @Column(name = "ROL")
  private String role;

  @Column(name = "ACCS_LS")
  @Convert(converter = ListStringConverter.class)
  private List<String> accessList;

  @Column(name = "IS_SUPERADMIN")
  private Boolean isSuperadmin;
}
