package com.avira.iot.interview.devices.util;

import com.avira.iot.interview.devices.dto.DeviceResource;
import com.avira.iot.interview.devices.types.DeviceAttribute;
import com.avira.iot.interview.devices.types.DeviceClassification;
import com.avira.iot.interview.devices.types.DeviceFilter;
import com.avira.iot.interview.devices.types.DeviceGroup;
import com.avira.iot.interview.devices.types.DeviceGroupData;
import com.avira.iot.interview.devices.types.DeviceLink;
import com.avira.iot.interview.devices.types.DeviceMeta;
import com.avira.iot.interview.devices.types.DeviceRelationship;
import com.avira.iot.interview.common.types.ResourceType;
import com.avira.iot.interview.devices.types.DeviceResponse;
import lombok.experimental.UtilityClass;
import lombok.val;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@UtilityClass
public class DeviceMapper {

    private static final String VENDOR = "vendor";
    private static final String USER_ID = "userId";
    private static final String DEVICE_CATEGORY_ID = "deviceCategoryId";

    private static Mono<DeviceGroupData> asGroupData(String group, Flux<DeviceClassification> devices) {
        return Mono.zip(Mono.just(group), devices.collectList())
                .map(res -> new DeviceGroupData(res.getT1(), res.getT2()));
    }

    public static Mono<DeviceGroup> asGroup(DeviceFilter filter,
                                            String group,
                                            Flux<DeviceClassification> devices) {
        return Mono.zip(
                Mono.just(UUID.randomUUID()),
                Mono.just(ResourceType.GROUP_STATS),
                asGroupData(group, devices),
                Mono.just(
                        new DeviceRelationship(
                                new DeviceLink("/devices?" + filter.getGroupBy() + "=" + group))
                )
        ).map(res -> new DeviceGroup(res.getT1(), res.getT2(), res.getT3(), res.getT4()));
    }

    public static Mono<DeviceResource> asDeviceResource(Mono<Long> totalUsers,
                                                        Mono<Long> totalDevices,
                                                        Flux<DeviceClassification> classifications,
                                                        Flux<DeviceGroup> groups) {
        return Mono.zip(
                totalDevices,
                totalUsers,
                classifications.collectList(),
                groups.collectList())
                .map(arr ->
                        new DeviceAttribute(arr.getT1(), arr.getT2(), arr.getT3(), arr.getT4())
                )
                .map(attribute ->
                        new DeviceResource(UUID.randomUUID(), ResourceType.DEVICE, attribute)
                );
    }

    public static Mono<DeviceResponse> asDeviceResponse(Mono<DeviceResource> deviceResource, Mono<DeviceMeta> deviceMeta) {
        return deviceResource.zipWith(deviceMeta, DeviceResponse::new);
    }

    public static DeviceFilter mutateFilter(DeviceFilter filter, String doc) {
        val newFilter = DeviceFilter.builder()
                .createdAfter(filter.getCreatedAfter())
                .deviceCategoryId(filter.getDeviceCategoryId())
                .deviceSubCategoryId(filter.getDeviceSubCategoryId())
                .groupBy(filter.getGroupBy())
                .userId(filter.getUserId())
                .vendor(filter.getVendor())
                .createdBefore(filter.getCreatedBefore())
                .updatedAfter(filter.getUpdatedAfter())
                .updatedBefore(filter.getUpdatedBefore());

        if (filter.getGroupBy().equals(VENDOR)) {
            newFilter.vendor(doc);
        } else if (filter.getGroupBy().equals(USER_ID)) {
            newFilter.userId(doc);
        } else if (filter.getGroupBy().equals(DEVICE_CATEGORY_ID)) {
            newFilter.deviceCategoryId(Integer.valueOf(doc));
        }
        return newFilter.build();
    }

}
