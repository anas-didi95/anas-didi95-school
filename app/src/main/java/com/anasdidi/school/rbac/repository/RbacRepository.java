/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.repository;

import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.entity.RbacEntity;
import com.anasdidi.school.user.entity.UserEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import io.micronaut.data.repository.jpa.JpaSpecificationExecutor;
import java.util.UUID;

@Repository(RbacConstants.CONNECTION_NAME)
public interface RbacRepository
    extends PageableRepository<RbacEntity, UUID>, JpaSpecificationExecutor<UserEntity> {}
