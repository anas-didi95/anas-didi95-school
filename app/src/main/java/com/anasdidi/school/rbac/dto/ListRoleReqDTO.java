/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Serdeable
@Builder
public record ListRoleReqDTO(
    String role, @Min(1) Integer pageNo, @Min(1) Integer totalRecordsPerPage)
    implements CommonReqDTO {}
