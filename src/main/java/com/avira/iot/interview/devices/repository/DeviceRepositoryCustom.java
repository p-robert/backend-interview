package com.avira.iot.interview.devices.repository;

import com.avira.iot.interview.devices.types.DeviceClassification;
import com.avira.iot.interview.devices.types.DeviceFilter;
import com.avira.iot.interview.devices.types.DeviceGroup;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DeviceRepositoryCustom {

    Mono<Long> totalDevices(DeviceFilter filter);
    Mono<Long> totalUsers(DeviceFilter filter);
    Flux<DeviceClassification> classifications(DeviceFilter filter);
    Flux<String> groupBy(String groupBy);

}
