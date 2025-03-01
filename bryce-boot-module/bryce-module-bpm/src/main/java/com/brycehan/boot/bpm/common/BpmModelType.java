package com.brycehan.boot.bpm.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BPM 模型的类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmModelType {

    BPMN(0, "BPMN 设计器"),
    SIMPLE(1, "SIMPLE 设计器"),
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
    public static BpmModelType of(Integer value) {
        for (BpmModelType genderType : values()) {
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
    public static BpmModelType getByDesc(String desc) {
        for (BpmModelType genderType : values()) {
            if (genderType.getDesc().equals(desc)) {
                return genderType;
            }
        }
        return null;
    }

}
