package com.project.webchatbe.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class UtilDateTime {
    private static final ZoneId VIETNAM_ZONE = ZoneId.of("Asia/Ho_Chi_Minh");

    public static LocalDateTime getLocalDateTimeVN() {
        return ZonedDateTime.now(VIETNAM_ZONE).toLocalDateTime();
    }
}
