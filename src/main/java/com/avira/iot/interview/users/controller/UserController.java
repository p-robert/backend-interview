package com.avira.iot.interview.users.controller;

import com.avira.iot.interview.common.assembler.UserResourceAssembler;
import com.avira.iot.interview.users.types.UserFilter;
import com.avira.iot.interview.common.util.DateHelper;
import com.avira.iot.interview.users.service.IUserService;
import com.avira.iot.interview.users.types.UserRequest;
import com.avira.iot.interview.users.types.UserResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final UserResourceAssembler userResourceAssembler;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<ResponseEntity<UserResponse>> createUser(
            @RequestBody @NonNull UserRequest userResource) {

        return userService.createUser(userResourceAssembler.toEntity(userResource))
                .map(user -> ResponseEntity.ok().body(user));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Mono<ResponseEntity<UserResponse>> getUserById(@PathVariable("id") String userId) {
        return userService.getUserById(userId)
                .map(user -> ResponseEntity.ok()
                .body(user));
    }

    @GetMapping
    public Mono<UserResponse> users(
            @RequestParam(value = "filter[createdAfter]", required = false) String createdAfter,
            @RequestParam(value = "filter[createdBefore]", required = false) String createdBefore,
            @RequestParam(value = "filter[updatedAfter]", required = false) String updatedAfter,
            @RequestParam(value = "filter[updatedBefore]", required = false) String updatedBefore) {
        UserFilter userFilter = UserFilter.builder()
                .createdAfter(DateHelper.toLocalDateTime(createdAfter))
                .createdBefore(DateHelper.toLocalDateTime(createdBefore))
                .updatedAfter(DateHelper.toLocalDateTime(updatedAfter))
                .updatedBefore(DateHelper.toLocalDateTime(updatedBefore))
                .build();
        return userService.findUsers(userFilter);
    }


}
