package cn.fenrana.blog.utils;

import java.time.Instant;

public class TimeUtils {

    private TimeUtils() {}

    public static Long getTimestamp() {
        Instant now = Instant.now();
        return now.toEpochMilli();
    }
}
