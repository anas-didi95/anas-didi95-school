/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder
public record HelloWorldReqDTO(String name) implements CommonReqDTO {}
