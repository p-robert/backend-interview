package com.avira.iot.interview.users.util;

import com.avira.iot.interview.users.types.UserFilter;
import com.avira.iot.interview.users.model.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.util.Optional;

@UtilityClass
public class PredicateBuilder {

    public static Predicate buildPredicate(UserFilter userFilter) {
        val qUser = QUser.user;

        val createdAfter = userFilter.getCreatedAfter();
        val createdBefore = userFilter.getCreatedBefore();
        val updatedBefore = userFilter.getUpdatedBefore();
        val updatedAfter = userFilter.getUpdatedAfter();

        val booleanBuilder = new BooleanBuilder();

        Optional.ofNullable(createdAfter).ifPresent(date -> booleanBuilder.and(qUser.createdAt.gt(date)));
        Optional.ofNullable(createdBefore).ifPresent(date -> booleanBuilder.and(qUser.createdAt.lt(date)));
        Optional.ofNullable(updatedAfter).ifPresent(date -> booleanBuilder.and(qUser.updatedAt.gt(date)));
        Optional.ofNullable(updatedBefore).ifPresent(date -> booleanBuilder.and(qUser.updatedAt.lt(date)));

        return booleanBuilder;
    }

}
