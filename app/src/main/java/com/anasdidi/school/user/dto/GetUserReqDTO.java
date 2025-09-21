/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Serdeable
@Builder
public record GetUserReqDTO(@NotNull UUID id) implements CommonReqDTO {}
