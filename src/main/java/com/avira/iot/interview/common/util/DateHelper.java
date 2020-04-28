package com.avira.iot.interview.common.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@UtilityClass
public class DateHelper {

    public static LocalDateTime toLocalDateTime(String localDateTime) {
        return Optional.ofNullable(localDateTime)
                .map(dateTime -> ZonedDateTime.parse(dateTime,  DateTimeFormatter.ISO_ZONED_DATE_TIME))
                .map(ZonedDateTime::toLocalDateTime)
                .orElse(null);
    }
}
