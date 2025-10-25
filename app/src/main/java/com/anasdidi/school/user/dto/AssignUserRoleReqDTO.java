/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import lombok.Builder;

@Serdeable
@Builder
public record AssignUserRoleReqDTO(
    @NotNull UUID id, @NotNull Integer version, List<String> roleList) implements CommonReqDTO {}
