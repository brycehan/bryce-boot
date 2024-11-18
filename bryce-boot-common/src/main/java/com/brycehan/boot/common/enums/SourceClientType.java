package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 请求来源客户端类型
 *
 * @author Bryce Han
 * @since 2024/4/7
 */
@Getter
public enum SourceClientType {
    PC("pc"),
    H5("h5"),
    APP("app"),
    MINI_APP("miniApp"),
    UNKNOWN("unknown"),
    ;

    @JsonValue
    private final String value;

    SourceClientType(String value) {
        this.value = value;
    }

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
