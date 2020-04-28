package com.avira.iot.interview.devices.service;

import com.avira.iot.interview.devices.types.DeviceFilter;
import com.avira.iot.interview.devices.types.DeviceResponse;
import reactor.core.publisher.Mono;

public interface IDeviceService {
    Mono<DeviceResponse> devices(DeviceFilter filter);
}
