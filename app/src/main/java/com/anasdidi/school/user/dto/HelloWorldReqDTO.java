/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Serdeable
@Builder
public record HelloWorldReqDTO(@NotBlank String name, String password) implements CommonReqDTO {}
