/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.timesheet.dto;

import com.anasdidi.school.common.dto.CommonResDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.time.OffsetDateTime;
import lombok.Builder;

@Serdeable
@Builder
public record CheckInResDTO(OffsetDateTime lastDateTime) implements CommonResDTO {}
