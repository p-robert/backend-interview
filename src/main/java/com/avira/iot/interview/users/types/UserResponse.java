package com.avira.iot.interview.users.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.util.List;

@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponse {
    List<UserResource> data;
    UserFilter meta;
}
