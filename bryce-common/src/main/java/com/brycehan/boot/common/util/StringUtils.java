package com.brycehan.boot.common.util;

import com.brycehan.boot.common.constant.CommonConstants;
import org.apache.commons.lang3.RegExUtils;

import java.util.Objects;

/**
 * @author Bryce Han
 * @since 2022/8/2
 */
public class StringUtils {

    /**
     * 解析字符串
     * <p>
     * 你好，{}，我是你孩子的老师{}，请你{}来参加家长会
     * params：{"奥巴马", "川普", "2016-12-12早8点"}
     * 你好，奥巴马，我是你孩子的老师川普，请你2016-12-12早8点来参加家长会
     *
     * @param rawValue
     * @param params
     * @return
     */
    public static String parseValue(String rawValue, String... params) {
        String value = rawValue;

        if (Objects.nonNull(params)) {
            for (String param : params) {
                value = RegExUtils.replaceFirst(value, CommonConstants.DELI_STR_PATTERN, param);
            }
        }
        return value;
    }

    /**
     * 将对象转换为字符串
     *
     * @param object       当前对象
     * @param defaultValue object为null时，默认值
     * @return
     */
    public static String convert(Object object, String defaultValue) {
        if (Objects.isNull(object)) {
            return defaultValue;
        }
        if (object instanceof String) {
            return (String) object;
        }
        return object.toString();
    }

}
