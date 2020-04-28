package com.avira.iot.interview.devices.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class DeviceDTO {
     String id;
     LocalDateTime createdAt;
     int deviceCategoryId;
     int deviceSubCategoryId;
     String deviceId;
     String userId;
     String vendor;
}
