package com.avira.iot.interview.users.service;

import com.avira.iot.interview.users.model.User;
import com.avira.iot.interview.users.types.UserFilter;
import com.avira.iot.interview.users.types.UserResponse;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<UserResponse> findUsers(UserFilter userFilter);
    Mono<UserResponse> createUser(User userResource);
    Mono<UserResponse> getUserById(String id);

}
