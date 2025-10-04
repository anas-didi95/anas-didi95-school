/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.entity;

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

  @Column(name = "ROLES")
  @Convert(converter = ListStringConverter.class)
  private List<String> roleList;
}
