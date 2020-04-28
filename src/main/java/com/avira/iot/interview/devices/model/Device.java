package com.avira.iot.interview.devices.model;

import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "devices")
@NoArgsConstructor
@Data
@QueryEntity
public class Device {

    @Id
    private String id;
    private LocalDateTime createdAt;
    private int deviceCategoryId;
    private int deviceSubCategoryId;

    @Field("id")
    private String deviceId;
    private String userId;
    private String vendor;

}
