/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto;

import com.anasdidi.school.common.dto.CommonReqDTO;
import com.anasdidi.school.user.dto.model.UserDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.util.UUID;
import lombok.Builder;

@Serdeable
@Builder
public record UpdateUserReqDTO(UUID id, UserDTO update) implements CommonReqDTO {}
