/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Serdeable
@Builder
public record ListUserReqDTO(
    String username, String name, @Min(1) Integer pageNo, Integer totalRecordsPerPage)
    implements CommonReqDTO {}
