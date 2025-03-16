package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 延迟器类型枚举
 *
 * @author Bryce Han
 * @since 2025/3/3
 */
@Getter
@AllArgsConstructor
public enum BpmDelayTimerTypeEnum {

    FIXED_TIME_DURATION(1, "固定时长"),
    FIXED_DATE_TIME(2, "固定日期");

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
    public static BpmDelayTimerTypeEnum of(Integer value) {
        for (BpmDelayTimerTypeEnum bpmProcessInstanceStatusEnum : values()) {
            if (bpmProcessInstanceStatusEnum.getValue().equals(value)) {
                return bpmProcessInstanceStatusEnum;
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
    public static BpmDelayTimerTypeEnum getByDesc(String desc) {
        for (BpmDelayTimerTypeEnum bpmProcessInstanceStatusEnum : values()) {
            if (bpmProcessInstanceStatusEnum.getDesc().equals(desc)) {
                return bpmProcessInstanceStatusEnum;
            }
        }
        return null;
    }

}
