package com.brycehan.boot.common.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author Bryce Han
 * @since 2022/5/16
 */
public class DateTimeUtils {

    /**
     * 日期类型转换
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        long milli = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return new Date(milli);
    }

    /**
     * 日期类型转换
     *
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.of("+8"));
    }

}
