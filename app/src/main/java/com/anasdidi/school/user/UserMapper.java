/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user;

import com.anasdidi.school.user.dto.SearchUserReqDTO;
import com.anasdidi.school.user.dto.model.UserDTO;
import com.anasdidi.school.user.entity.UserEntity;
import io.micronaut.context.annotation.Mapper;
import java.util.Map;

public interface UserMapper {

  @Mapper
  UserDTO toUserDTO(UserEntity entity);

  @Mapper
  SearchUserReqDTO toSearchUserReqDTO(Map<String, String> in);
}
