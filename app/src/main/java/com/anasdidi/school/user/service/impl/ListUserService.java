/* (C) Anas Juwaidi Bin Mohd Jeffry. All rights reserved. */
package com.anasdidi.school.user.service.impl;

import com.anasdidi.school.common.dto.PaginationDTO;
import com.anasdidi.school.user.UserConstants;
import com.anasdidi.school.user.UserMapper;
import com.anasdidi.school.user.dto.ListUserReqDTO;
import com.anasdidi.school.user.dto.ListUserResDTO;
import com.anasdidi.school.user.entity.UserEntity;
import com.anasdidi.school.user.entity.UserEntity_;
import com.anasdidi.school.user.repository.UserRepository;
import com.anasdidi.school.user.service.UserService;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Named(UserConstants.Event.USER_LIST_USER)
@RequiredArgsConstructor
@Slf4j
class ListUserService extends UserService<ListUserReqDTO, ListUserResDTO> {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  @Override
  protected ListUserResDTO execute(ListUserReqDTO in) {
    log.trace("START...");

    int pageNo = Optional.ofNullable(in.pageNo()).orElse(1);
    int totalRecordsPerPage = Optional.ofNullable(in.totalRecordsPerPage()).orElse(10);
    Pageable pageable = Pageable.from(pageNo - 1, totalRecordsPerPage);

    Page<UserEntity> search =
        userRepository.findAll(
            (root, criteriaBuilder) -> {
              List<Predicate> list = new ArrayList<>();

              Optional.ofNullable(in.username())
                  .ifPresent(
                      t ->
                          list.add(
                              criteriaBuilder.equal(
                                  root.get(UserEntity_.USERNAME), in.username())));

              Optional.ofNullable(in.name())
                  .ifPresent(
                      v -> {
                        Expression<String> expr = criteriaBuilder.lower(root.get(UserEntity_.NAME));
                        list.add(criteriaBuilder.like(expr, "%" + in.name().toLowerCase() + "%"));
                      });

              if (!list.isEmpty()) {
                return criteriaBuilder.and(list.toArray(Predicate[]::new));
              }
              return null;
            },
            pageable);

    log.debug("List completed...");
    return ListUserResDTO.builder()
        .resultList(search.getContent().stream().map(userMapper::toUserDTO).toList())
        .pagination(
            PaginationDTO.builder()
                .pageNo(pageNo)
                .totalRecords(search.getTotalSize())
                .totalRecordsPerPage(totalRecordsPerPage)
                .build())
        .build();
  }
}
