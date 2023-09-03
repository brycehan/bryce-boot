package com.brycehan.boot.common.util;

import org.apache.commons.lang3.RegExUtils;

/**
 * 字符串格式化工具类
 *
 * @author Bryce Han
 * @since 2022/11/3
 */
public class StringFormatUtils {

    /**
     * 格式化字符串
     * <p>
     * 你好，{}，我是你孩子的老师{}，请你{}来参加家长会
     * params：{"奥巴马", "川普", "2016-12-12早8点"}
     * 你好，奥巴马，我是你孩子的老师川普，请你2016-12-12早8点来参加家长会
     *
     * @param format 需要格式化的文本
     * @param params 参数
     * @return 处理后的文本
     */
    public static String format(String format, String... params) {
        for (String param : params) {
            format = RegExUtils.replaceFirst(format, "\\{\\}", param);
        }
        return format;
    }
}
