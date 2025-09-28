/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto.model;

import com.anasdidi.school.common.dto.Views;
import com.fasterxml.jackson.annotation.JsonView;
import io.micronaut.serde.annotation.Serdeable;
import java.time.OffsetDateTime;
import java.util.UUID;

@Serdeable
@JsonView(Views.Public.class)
public record UserDTO(
    UUID id,
    Boolean isDeleted,
    Integer version,
    String createBy,
    OffsetDateTime createDate,
    String updateBy,
    OffsetDateTime updateDate,
    String username,
    @JsonView(Views.Internal.class) String password,
    String name,
    OffsetDateTime lastSigninDate) {}
