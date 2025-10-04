/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.auth.repository;

import com.anasdidi.school.auth.AuthConstants;
import com.anasdidi.school.auth.entity.AuthEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.UUID;

@Repository(AuthConstants.CONNECTION_NAME)
public interface AuthRepository
    extends PageableRepository<AuthEntity, UUID>, JpaSpecificationExecutor<AuthEntity> {

  Optional<AuthEntity> findByUsername(String username);
}
