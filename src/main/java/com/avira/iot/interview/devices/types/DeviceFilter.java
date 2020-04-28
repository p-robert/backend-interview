package com.avira.iot.interview.devices.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeviceFilter {

    @JsonProperty("filter[groupBy]")
    String groupBy;
    @JsonProperty("filter[userId]")
    String userId;
    @JsonProperty("filter[vendor]")
    String vendor;
    @JsonProperty("filter[deviceCategoryId]")
    Integer deviceCategoryId;
    @JsonProperty("filter[deviceSubCategoryId]")
    Integer deviceSubCategoryId;
    @JsonProperty("filter[createdAfter]")
    LocalDateTime createdAfter;
    @JsonProperty("filter[createdBefore]")
    LocalDateTime createdBefore;
    @JsonProperty("filter[updatedAfter]")
    LocalDateTime updatedAfter;
    @JsonProperty("filter[updatedBefore]")
    LocalDateTime updatedBefore;


}