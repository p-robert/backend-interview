package com.avira.iot.interview.users.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "users")
@NoArgsConstructor
@Data
@QueryEntity
public class User {

    @Id
    private String id;
    @Field("id")
    private String userId;
    private String name;
    private int age;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
