/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.dto;

import com.anasdidi.school.common.dto.CommonResDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import lombok.Builder;

@Serdeable
@Builder
public record ListAccessResDTO(List<String> resultList) implements CommonResDTO {}
