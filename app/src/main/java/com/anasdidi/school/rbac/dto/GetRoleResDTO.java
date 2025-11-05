/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.dto;

import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.rbac.dto.model.RoleDTO;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder
public record GetRoleResDTO(RoleDTO result) implements CommonResDTO {}
