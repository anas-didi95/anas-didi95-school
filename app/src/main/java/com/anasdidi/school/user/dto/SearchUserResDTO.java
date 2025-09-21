/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.dto;

import com.anasdidi.school.common.dto.CommonResDTO;
import com.anasdidi.school.user.dto.model.UserDTO;
import io.micronaut.serde.annotation.Serdeable;
import java.util.List;
import lombok.Builder;

@Serdeable
@Builder
public record SearchUserResDTO(List<UserDTO> resultList, Pagination pagination)
    implements CommonResDTO {

  @Serdeable
  @Builder
  public static record Pagination(int pageNo, long totalRecords, int totalRecordsPerPage) {}
}
