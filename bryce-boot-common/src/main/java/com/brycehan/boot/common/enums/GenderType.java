package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 性别类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum GenderType {

    MALE("M", "男"),
    FEMALE("F", "女"),
    UNKNOWN("N", "未知");

    @EnumValue
    @JsonValue
    private final String value;

    @DescValue
    private final String desc;

    /**
     * 根据值获取枚举类型
     *
     * @param value 值
     * @return 枚举类型
     */
    public static GenderType of(String value) {
        for (GenderType genderType : values()) {
            if (genderType.getValue().equals(value)) {
                return genderType;
            }
        }
        return null;
    }

    /**
     * 根据描述获取枚举类型
     *
     * @param desc 描述
     * @return 枚举类型
     */
    public static GenderType getByDesc(String desc) {
        for (GenderType genderType : values()) {
            if (genderType.getDesc().equals(desc)) {
                return genderType;
            }
        }
        return null;
    }

}
