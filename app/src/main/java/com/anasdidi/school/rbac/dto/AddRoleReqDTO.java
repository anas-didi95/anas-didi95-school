/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import lombok.Builder;

@Serdeable
@Builder
public record AddRoleReqDTO(String role, List<String> accessList) implements CommonReqDTO {}
