/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.dto;

import com.anasdidi.school.common.dto.CommonResDTO;
import io.micronaut.security.token.render.AccessRefreshToken;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder
public record SignInResDTO(AccessRefreshToken token) implements CommonResDTO {}
