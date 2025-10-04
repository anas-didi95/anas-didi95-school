/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import lombok.Builder;

@Serdeable
@Builder
public record CheckAccessReqDTO(@NotNull Collection<String> roleList, @NotBlank String access)
    implements CommonReqDTO {}
