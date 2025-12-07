/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.dto;

import com.anasdidi.school.common.dto.CommonResDTO;
import io.micronaut.security.endpoints.introspection.IntrospectionResponse;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder
public record TokenInfoResDTO(IntrospectionResponse result) implements CommonResDTO {}
