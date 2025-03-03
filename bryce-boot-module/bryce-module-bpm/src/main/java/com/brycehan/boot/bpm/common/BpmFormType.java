package com.brycehan.boot.bpm.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BPM 表单的类型
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmFormType {

    NORMAL(0, "流程表单"),
    CUSTOM(1, "业务表单"),
    ;

    @EnumValue
    @JsonValue
    private final Integer value;

    @DescValue
    private final String desc;

    /**
     * 根据值获取枚举类型
     *
     * @param value 值
     * @return 枚举类型
     */
    public static BpmFormType of(Integer value) {
        for (BpmFormType genderType : values()) {
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
    public static BpmFormType getByDesc(String desc) {
        for (BpmFormType genderType : values()) {
            if (genderType.getDesc().equals(desc)) {
                return genderType;
            }
        }
        return null;
    }

}
