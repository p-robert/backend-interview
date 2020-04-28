package com.avira.iot.interview.devices.types;

import lombok.Value;

import java.util.List;

@Value
public class DeviceGroupData {
    String name;
    List<DeviceClassification> classification;
}
