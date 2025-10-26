/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.rbac.dto.model.RoleDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Serdeable
@Builder
public record UpdateRoleReqDTO(@NotNull UUID id, @NotNull RoleDTO update) implements CommonReqDTO {}
