/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.dto.model;

import io.micronaut.serde.annotation.Serdeable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Serdeable
public record RoleDTO(
    UUID id,
    Boolean isDeleted,
    Integer version,
    String createBy,
    OffsetDateTime createDate,
    String updateBy,
    OffsetDateTime updateDate,
    String role,
    List<String> accessList) {}
