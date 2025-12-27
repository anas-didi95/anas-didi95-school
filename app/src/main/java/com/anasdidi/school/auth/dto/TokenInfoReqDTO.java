/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Serdeable
@Builder
public record TokenInfoReqDTO(
    @NotNull HttpRequest<?> request, @NotNull Authentication authentication)
    implements CommonReqDTO {}
