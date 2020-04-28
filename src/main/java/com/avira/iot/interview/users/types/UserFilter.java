package com.avira.iot.interview.users.types;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserFilter {

    @JsonProperty("filter[createdAfter]")
    LocalDateTime createdAfter;
    @JsonProperty("filter[createdBefore]")
    LocalDateTime createdBefore;
    @JsonProperty("filter[updatedAfter]")
    LocalDateTime updatedAfter;
    @JsonProperty("filter[updatedBefore]")
    LocalDateTime updatedBefore;

}
