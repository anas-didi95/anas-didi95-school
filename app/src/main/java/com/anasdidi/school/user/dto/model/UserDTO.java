/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto.model;

import io.micronaut.serde.annotation.Serdeable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Serdeable
public record UserDTO(
    UUID id,
    Boolean isDeleted,
    Integer version,
    String createBy,
    OffsetDateTime createDate,
    String updateBy,
    OffsetDateTime updateDate,
    String username,
    String name,
    OffsetDateTime lastSigninDate) {}
