/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.repository;

import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.entity.UserEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import java.util.Optional;
import java.util.UUID;

@Repository(UserConstants.CONNECTION_NAME)
public interface UserRepository extends PageableRepository<UserEntity, UUID> {

  Optional<UserEntity> findByUsername(String username);
}
