package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BPM 多人审批方式的枚举
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmUserTaskApproveMethodEnum {

    RANDOM(1, "随机挑选一人审批", null),
    RATIO(2, "多人会签(按通过比例)", "${ nrOfCompletedInstances/nrOfInstances >= %s}"), // 会签（按通过比例）
    ANY(3, "多人或签(一人通过或拒绝)", "${ nrOfCompletedInstances > 0 }"), // 或签（通过只需一人，拒绝只需一人）
    SEQUENTIAL(4, "依次审批", "${ nrOfCompletedInstances >= nrOfInstances }"); // 依次审批

    @EnumValue
    @JsonValue
    private final Integer value;

    @DescValue
    private final String desc;

    /**
     * 完成表达式
     */
    private final String completionCondition;

    /**
     * 根据值获取枚举类型
     *
     * @param value 值
     * @return 枚举类型
     */
    public static BpmUserTaskApproveMethodEnum of(Integer value) {
        for (BpmUserTaskApproveMethodEnum handlerTypeEnum : values()) {
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
    public static BpmUserTaskApproveMethodEnum getByDesc(String desc) {
        for (BpmUserTaskApproveMethodEnum handlerTypeEnum : values()) {
            if (handlerTypeEnum.getDesc().equals(desc)) {
                return handlerTypeEnum;
            }
        }
        return null;
    }

}
