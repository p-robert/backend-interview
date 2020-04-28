package com.avira.iot.interview.devices.controller;

import com.avira.iot.interview.common.util.DateHelper;
import com.avira.iot.interview.devices.service.IDeviceService;
import com.avira.iot.interview.devices.types.DeviceFilter;
import com.avira.iot.interview.devices.types.DeviceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stats/devices")
public class DeviceController {

    private final IDeviceService deviceService;

    @GetMapping
    public Mono<DeviceResponse> devices(
            @RequestParam(value = "filter[groupBy]") String groupBy,
            @RequestParam(value = "filter[userId]", required = false) String userId,
            @RequestParam(value = "filter[vendor]", required = false) String vendor,
            @RequestParam(value = "filter[deviceCategoryId]", required = false) Integer deviceCategoryId,
            @RequestParam(value = "filter[deviceSubcategoryId]", required = false) Integer deviceSubCategoryId,
            @RequestParam(value = "filter[createdAfter]", required = false) String createdAfter,
            @RequestParam(value = "filter[createdBefore]", required = false) String createdBefore,
            @RequestParam(value = "filter[updatedAfter]", required = false) String updatedAfter,
            @RequestParam(value = "filter[updatedBefore]", required = false) String updatedBefore) {
        DeviceFilter deviceFilter = DeviceFilter.builder()
                .groupBy(groupBy)
                .userId(userId)
                .vendor(vendor)
                .deviceCategoryId(deviceCategoryId)
                .deviceSubCategoryId(deviceSubCategoryId)
                .createdAfter(DateHelper.toLocalDateTime(createdAfter))
                .createdBefore(DateHelper.toLocalDateTime(createdBefore))
                .updatedAfter(DateHelper.toLocalDateTime(updatedAfter))
                .updatedBefore(DateHelper.toLocalDateTime(updatedBefore))
                .build();
        return deviceService.devices(deviceFilter);
    }

}
