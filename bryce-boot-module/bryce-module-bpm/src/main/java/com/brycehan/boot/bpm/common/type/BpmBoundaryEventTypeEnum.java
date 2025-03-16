package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 边界事件 (boundary event) 自定义类型枚举
 *
 * @author Bryce Han
 * @since 2025/3/3
 */
@Getter
@AllArgsConstructor
public enum BpmBoundaryEventTypeEnum {

    USER_TASK_TIMEOUT(1, "用户任务超时"),
    DELAY_TIMER_TIMEOUT(2, "延迟器超时");

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
    public static BpmBoundaryEventTypeEnum of(Integer value) {
        for (BpmBoundaryEventTypeEnum bpmProcessInstanceStatusEnum : values()) {
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
    public static BpmBoundaryEventTypeEnum getByDesc(String desc) {
        for (BpmBoundaryEventTypeEnum bpmProcessInstanceStatusEnum : values()) {
            if (bpmProcessInstanceStatusEnum.getDesc().equals(desc)) {
                return bpmProcessInstanceStatusEnum;
            }
        }
        return null;
    }

}
