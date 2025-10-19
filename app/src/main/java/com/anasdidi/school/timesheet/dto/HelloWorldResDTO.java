/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.dto;

import com.anasdidi.school.common.dto.CommonResDTO;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder
public record HelloWorldResDTO(String greeting) implements CommonResDTO {}
