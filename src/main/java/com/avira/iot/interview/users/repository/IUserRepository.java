package com.avira.iot.interview.users.repository;

import com.avira.iot.interview.users.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IUserRepository extends ReactiveMongoRepository<User, String>,
        ReactiveQuerydslPredicateExecutor<User> {
    Mono<User> findOneByUserId(String userId);
}
