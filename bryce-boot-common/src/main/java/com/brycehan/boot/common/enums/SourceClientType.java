package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 请求来源客户端类型
 *
 * @author Bryce Han
 * @since 2024/4/7
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum SourceClientType implements EnumType {

    PC("pc", "PC"),
    H5("h5", "H5"),
    APP("app", "APP"),
    MINI_APP("miniApp", "小程序"),
    UNKNOWN("unknown", "未知"),
    ;

    /**
     * 值
     */
    @JsonValue
    private final String value;

    /**
     * 描述
     */
    private final String desc;

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

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static SourceClientType getByDesc(String desc) {
        for (SourceClientType sourceClientType : values()) {
            if (sourceClientType.desc.equals(desc)) {
                return sourceClientType;
            }
        }
        return null;
    }

}
