package com.avira.iot.interview.common.util;

import lombok.Value;

import java.util.List;

@Value
public class ApiErrorsResponse {
    List<ApiErrorResponse> errors;
}
