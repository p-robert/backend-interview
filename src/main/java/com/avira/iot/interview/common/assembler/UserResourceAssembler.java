package com.avira.iot.interview.common.assembler;

import com.avira.iot.interview.users.types.UserFilter;
import com.avira.iot.interview.users.types.UserRequest;
import com.avira.iot.interview.users.types.UserResponse;
import com.avira.iot.interview.common.types.ResourceType;
import com.avira.iot.interview.users.dto.UserDTO;
import com.avira.iot.interview.users.types.UserResource;
import com.avira.iot.interview.users.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserResourceAssembler {

    private final ModelMapper modelMapper;

    public UserDTO toDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User toEntity(UserDTO user) {
        return modelMapper.map(user, User.class);
    }

    public User toEntity(@NonNull UserRequest user) {
        val newUser = toEntity(user.getData().getAttributes());
        val now = LocalDateTime.now();
        newUser.setCreatedAt(now);
        newUser.setUpdatedAt(now);
        newUser.setId(user.getData().getId());
        return newUser;
    }

    public UserResponse toUserResponse(List<UserResource> userResource, UserFilter userFilter) {
        return new UserResponse(userResource, userFilter);
    }

    public UserResource toUserResource(User user) {
        return new UserResource(user.getUserId(), ResourceType.USERS, toDto(user));
    }
}
