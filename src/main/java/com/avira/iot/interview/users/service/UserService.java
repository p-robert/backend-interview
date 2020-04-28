package com.avira.iot.interview.users.service;

import com.avira.iot.interview.common.assembler.UserResourceAssembler;
import com.avira.iot.interview.common.exception.DataAlreadyExistsException;
import com.avira.iot.interview.common.exception.DataNotFoundException;
import com.avira.iot.interview.users.types.UserFilter;
import com.avira.iot.interview.users.types.UserResponse;
import com.avira.iot.interview.users.model.User;
import com.avira.iot.interview.users.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.avira.iot.interview.users.util.PredicateBuilder.buildPredicate;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository IUserRepository;
    private final UserResourceAssembler resourceAssembler;

    public Mono<UserResponse> findUsers(UserFilter userFilter) {
        val predicate = buildPredicate(userFilter);
        return toUserResponse(IUserRepository.findAll(predicate), userFilter);
    }

    @Override
    public Mono<UserResponse> createUser(User user) {
        return findUserById(user).switchIfEmpty(saveUser(user)).cast(UserResponse.class);
    }

    private Mono<UserResponse> saveUser(User user) {
        return Mono.defer(() -> IUserRepository.save(user)
                .map(resourceAssembler::toUserResource)
                .flatMap(Mono::justOrEmpty)
                .map(userResource ->
                        resourceAssembler.toUserResponse(
                                List.of(userResource),
                                UserFilter
                                        .builder()
                                        .createdAfter(userResource.getAttributes().getCreatedAt())
                                        .build())
                ));
    }

    private Mono<Object> findUserById(User user) {
        return IUserRepository
                .findOneByUserId(user.getUserId())
                .flatMap(existingUser ->
                        Mono.error(
                                new DataAlreadyExistsException("User with id [" + existingUser.getUserId() + "] and username [" + existingUser.getName() + "] already exists!")
                        )
                );
    }

    @Override
    public Mono<UserResponse> getUserById(String id) {
        return IUserRepository.findOneByUserId(id)
                .map(resourceAssembler::toUserResource)
                .map(dbUser -> resourceAssembler.toUserResponse(List.of(dbUser), null))
                .switchIfEmpty(Mono.error(new DataNotFoundException("User with id [" + id + "] does not exist!")));
    }

    private Mono<UserResponse> toUserResponse(Flux<User> users, UserFilter userFilter) {
        return Mono.zip(
                users.collectList()
                        .map(list -> list.stream()
                                .map(resourceAssembler::toUserResource)
                                .collect(toList())
                        ),
                Mono.just(userFilter))
                        .map(res -> resourceAssembler.toUserResponse(res.getT1(), res.getT2())
                );
    }
}
