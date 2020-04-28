package com.avira.iot.interview.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class UserDTO {
    @JsonIgnore
    private String id;
    private String userId;
    protected String name;
    protected int age;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

}
