package com.avira.iot.interview.devices.types;

import com.avira.iot.interview.common.types.ResourceType;
import lombok.Value;

import java.util.UUID;

@Value
public class DeviceGroup {
    UUID id;
    ResourceType type;
    DeviceGroupData data;
    DeviceRelationship relationships;
}
