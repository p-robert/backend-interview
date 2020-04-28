package com.avira.iot.interview.devices.dto;

import com.avira.iot.interview.devices.types.DeviceAttribute;
import com.avira.iot.interview.common.types.ResourceType;
import lombok.Value;

import java.util.UUID;

@Value
public class DeviceResource {
    UUID id;
    ResourceType type;
    DeviceAttribute attributes;
}
