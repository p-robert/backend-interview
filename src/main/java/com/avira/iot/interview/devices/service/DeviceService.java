package com.avira.iot.interview.devices.service;

import com.avira.iot.interview.devices.repository.DeviceRepository;
import com.avira.iot.interview.devices.types.DeviceClassification;
import com.avira.iot.interview.devices.types.DeviceFilter;
import com.avira.iot.interview.devices.types.DeviceGroup;
import com.avira.iot.interview.devices.types.DeviceMeta;
import com.avira.iot.interview.devices.types.DeviceResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.avira.iot.interview.devices.util.DeviceMapper.asDeviceResource;
import static com.avira.iot.interview.devices.util.DeviceMapper.asDeviceResponse;
import static com.avira.iot.interview.devices.util.DeviceMapper.asGroup;
import static com.avira.iot.interview.devices.util.DeviceMapper.mutateFilter;
import static reactor.core.publisher.Mono.defer;

@Service
@RequiredArgsConstructor
public class DeviceService implements IDeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public Mono<DeviceResponse> devices(DeviceFilter filter) {
        val totalUsers = getTotalUsers(filter);
        val totalDevices = getTotalDevices(filter);
        val classifications = getClassifications(filter);
        val groups = getGroups(filter);
        val deviceMeta = getDeviceMeta(filter);

        return buildResponse(totalUsers, totalDevices, classifications, groups, deviceMeta);
    }

    private Mono<DeviceResponse> buildResponse(Mono<Long> totalUsers,
                                               Mono<Long> totalDevices,
                                               Flux<DeviceClassification> classifications,
                                               Flux<DeviceGroup> groups,
                                               Mono<DeviceMeta> deviceMeta) {
        val deviceResource = asDeviceResource(totalUsers, totalDevices, classifications, groups);

        return asDeviceResponse(deviceResource, deviceMeta);
    }

    private Flux<DeviceGroup> getGroups(DeviceFilter filter) {
        return deviceRepository
                .groupBy(filter.getGroupBy())
                .flatMap(doc -> toGroup(filter, doc));
    }

    private Flux<DeviceClassification> getClassifications(DeviceFilter filter) {
        return deviceRepository.classifications(filter);
    }

    private Mono<DeviceGroup> toGroup(DeviceFilter filter, String doc) {
        val newFilter = mutateFilter(filter, doc);

        return asGroup(filter, doc, getClassifications(newFilter));
    }

    private Mono<Long> getTotalDevices(DeviceFilter filter) {
        return deviceRepository
                .totalDevices(filter)
                .switchIfEmpty(
                        defer(() -> deviceRepository.findAll().count())
                );
    }

    private Mono<Long> getTotalUsers(DeviceFilter filter) {
        return deviceRepository
                .totalUsers(filter)
                .switchIfEmpty(
                        defer(() -> deviceRepository.findAll().count())
                );
    }

    private Mono<DeviceMeta> getDeviceMeta(DeviceFilter filter) {
        return Mono.justOrEmpty(filter).map(DeviceMeta::new);
    }

}
