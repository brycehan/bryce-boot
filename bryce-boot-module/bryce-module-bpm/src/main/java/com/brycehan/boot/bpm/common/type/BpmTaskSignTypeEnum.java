package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流程任务的加签类型枚举
 *
 * @author Bryce Han
 * @since 2025/3/3
 */
@Getter
@AllArgsConstructor
public enum BpmTaskSignTypeEnum {

    /**
     * 向前加签，需要前置任务审批完成，才回到原审批人
     */
    BEFORE("before", "向前加签"),
    /**
     * 向后加签，需要后置任务全部审批完，才会通过原审批人节点
     */
    AFTER("after", "向后加签");

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
    public static BpmTaskSignTypeEnum of(String value) {
        for (BpmTaskSignTypeEnum bpmProcessInstanceStatusEnum : values()) {
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
    public static BpmTaskSignTypeEnum getByDesc(String desc) {
        for (BpmTaskSignTypeEnum bpmProcessInstanceStatusEnum : values()) {
            if (bpmProcessInstanceStatusEnum.getDesc().equals(desc)) {
                return bpmProcessInstanceStatusEnum;
            }
        }
        return null;
    }

}
