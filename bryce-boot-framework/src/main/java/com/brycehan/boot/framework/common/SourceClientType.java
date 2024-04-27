package com.brycehan.boot.framework.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 请求来源客户端类型
 *
 * @author Bryce Han
 * @since 2024/4/7
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum SourceClientType {
    PC("pc"),
    H5("h5"),
    APP("app"),
    MINI_APP("miniApp"),
    ;

    private final String value;

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static SourceClientType getByValue(String value) {
        for (SourceClientType sourceClientType : values()) {
            if (sourceClientType.value.equals(value)) {
                return sourceClientType;
            }
        }
        return null;
    }
}
