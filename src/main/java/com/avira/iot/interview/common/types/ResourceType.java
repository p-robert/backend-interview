package com.avira.iot.interview.common.types;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResourceType {
    DEVICE("deviceStats"), USERS("users"), GROUP_STATS("devicesStatsGroup");

    private final String type;

    ResourceType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
