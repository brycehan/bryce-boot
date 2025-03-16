package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BPM Simple 触发器类型枚举
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmTriggerTypeEnum {

    HTTP_REQUEST(1, "发起 HTTP 请求"),
    UPDATE_NORMAL_FORM(2, "更新流程表单");

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
    public static BpmTriggerTypeEnum of(Integer value) {
        for (BpmTriggerTypeEnum handlerTypeEnum : values()) {
            if (handlerTypeEnum.getValue().equals(value)) {
                return handlerTypeEnum;
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
    public static BpmTriggerTypeEnum getByDesc(String desc) {
        for (BpmTriggerTypeEnum handlerTypeEnum : values()) {
            if (handlerTypeEnum.getDesc().equals(desc)) {
                return handlerTypeEnum;
            }
        }
        return null;
    }

}
