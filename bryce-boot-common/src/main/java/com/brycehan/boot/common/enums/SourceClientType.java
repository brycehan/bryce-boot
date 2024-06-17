package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

/**
 * 请求来源客户端类型
 *
 * @author Bryce Han
 * @since 2024/4/7
 */
@RequiredArgsConstructor
public enum SourceClientType {
    PC("pc"),
    H5("h5"),
    APP("app"),
    MINI_APP("miniApp"),
    ;

    private final String value;

    @JsonValue
    public String value() {
        return value;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SourceClientType getByValue(String value) {
        for (SourceClientType sourceClientType : values()) {
            if (sourceClientType.value.equals(value)) {
                return sourceClientType;
            }
        }
        return null;
    }

}
