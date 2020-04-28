package com.avira.iot.interview.devices.types;

import com.avira.iot.interview.devices.dto.DeviceResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceResponse {
    DeviceResource data;
    DeviceMeta meta;
}
