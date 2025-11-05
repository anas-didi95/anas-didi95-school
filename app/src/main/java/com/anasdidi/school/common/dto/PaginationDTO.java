/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.common.dto;

import io.micronaut.serde.annotation.Serdeable;
import lombok.Builder;

@Serdeable
@Builder
public record PaginationDTO(int pageNo, long totalRecords, int totalRecordsPerPage) {}
