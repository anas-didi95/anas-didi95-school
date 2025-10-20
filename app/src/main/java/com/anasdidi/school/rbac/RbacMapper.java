/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac;

import com.anasdidi.school.rbac.dto.model.RoleDTO;
import com.anasdidi.school.rbac.entity.RbacEntity;
import io.micronaut.context.annotation.Mapper;

public interface RbacMapper {

  @Mapper
  RoleDTO toRoleDTO(RbacEntity entity);
}
