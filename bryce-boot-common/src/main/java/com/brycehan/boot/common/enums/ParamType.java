package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * 参数类型
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum ParamType {

    SYSTEM(0, "内置"),
    APP(1, "应用");

    /**
     * 类型值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 描述
     */
    @DescValue
    private final String desc;

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static ParamType of(Integer value) {
        for (ParamType paramType : values()) {
            if (paramType.getValue().equals(value)) {
                return paramType;
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
    public static ParamType getByDesc(String desc) {
        for (ParamType paramType : values()) {
            if (paramType.getDesc().equals(desc)) {
                return paramType;
            }
        }
        return null;
    }

}
