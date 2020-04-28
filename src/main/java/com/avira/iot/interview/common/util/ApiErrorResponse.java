package com.avira.iot.interview.common.util;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public final class ApiErrorResponse {
     String id;
     int code;
     HttpStatus status;
     String title;
     String detail;

}
