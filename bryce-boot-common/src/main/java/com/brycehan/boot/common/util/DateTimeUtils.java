package com.brycehan.boot.common.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * 日期时间工具类
 *
 * @since 2022/5/16
 * @author Bryce Han
 */
public class DateTimeUtils {

    public static final String yyyy_MM_dd = "yyyy_MM_dd";

    public final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.SIMPLIFIED_CHINESE).withZone(ZoneOffset.of("Asia/Shanghai"));

    /**
     * 日期类型转换
     *
     * @param localDateTime 日期时间
     * @return Date格式的日期
     */
    public static Date toDate(LocalDateTime localDateTime) {
        long milli = localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        return new Date(milli);
    }

    /**
     * 日期类型转换
     *
     * @param date 日期
     * @return 日期时间
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.of("+8"));
    }

    public static String today() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(yyyy_MM_dd));
    }
}
