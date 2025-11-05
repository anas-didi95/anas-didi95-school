/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.rbac.service.impl;

import com.anasdidi.school.common.dto.PaginationDTO;
import com.anasdidi.school.rbac.RbacConstants;
import com.anasdidi.school.rbac.RbacMapper;
import com.anasdidi.school.rbac.dto.ListRoleReqDTO;
import com.anasdidi.school.rbac.dto.ListRoleResDTO;
import com.anasdidi.school.rbac.entity.RbacEntity_;
import com.anasdidi.school.rbac.repository.RbacRepository;
import com.anasdidi.school.rbac.service.RbacService;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(RbacConstants.Event.RBAC_LIST_ROLE)
@RequiredArgsConstructor
@Slf4j
class ListRoleService extends RbacService<ListRoleReqDTO, ListRoleResDTO> {

  private final RbacRepository rbacRepository;
  private final RbacMapper rbacMapper;

  @Override
  protected ListRoleResDTO execute(ListRoleReqDTO in) {
    log.trace("START...");

    var pageNo = Optional.ofNullable(in.pageNo()).orElse(1);
    var totalRecordsPerPage = Optional.ofNullable(in.totalRecordsPerPage()).orElse(10);
    var pageable = Pageable.from(pageNo - 1, totalRecordsPerPage);

    var search =
        rbacRepository.findAll(
            (root, criteriaBuilder) -> {
              var where = new ArrayList<Predicate>();

              Optional.ofNullable(in.role())
                  .ifPresent(
                      v ->
                          where.add(
                              criteriaBuilder.like(
                                  root.get(RbacEntity_.ROLE), "%" + v.toUpperCase() + "%")));
              where.add(criteriaBuilder.isFalse(root.get(RbacEntity_.IS_SUPERADMIN)));

              return criteriaBuilder.and(where.toArray(Predicate[]::new));
            },
            pageable);

    log.debug("List completed...");
    return ListRoleResDTO.builder()
        .resultList(search.getContent().stream().map(rbacMapper::toRoleDTO).toList())
        .pagination(
            PaginationDTO.builder()
                .pageNo(pageNo)
                .totalRecords(search.getTotalSize())
                .totalRecordsPerPage(totalRecordsPerPage)
                .build())
        .build();
  }
}
