package com.avira.iot.interview.users.types;

import com.avira.iot.interview.common.types.ResourceType;
import com.avira.iot.interview.users.dto.UserDTO;
import lombok.Value;

@Value
public class UserResource<T extends UserDTO> {
    String id;
    ResourceType type;
    T attributes;
}
