package com.avira.iot.interview.devices.types;

import lombok.Value;
import java.util.List;

@Value
public class DeviceAttribute {
    Long totalDevices;
    Long totalUsers;
    List<DeviceClassification> classification;
    List<DeviceGroup> groups;
}
