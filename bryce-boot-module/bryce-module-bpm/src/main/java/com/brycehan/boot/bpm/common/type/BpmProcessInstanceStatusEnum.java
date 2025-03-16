package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 流程实例 ProcessInstance 的状态
 *
 * @author Bryce Han
 * @since 2025/3/3
 */
@Getter
@AllArgsConstructor
public enum BpmProcessInstanceStatusEnum {

    NOT_START(-1, "未开始"),
    RUNNING(1, "审批中"),
    APPROVE(2, "审批通过"),
    REJECT(3, "审批不通过"),
    CANCEL(4, "已取消");

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
    public static BpmProcessInstanceStatusEnum of(Integer value) {
        for (BpmProcessInstanceStatusEnum bpmProcessInstanceStatusEnum : values()) {
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
    public static BpmProcessInstanceStatusEnum getByDesc(String desc) {
        for (BpmProcessInstanceStatusEnum bpmProcessInstanceStatusEnum : values()) {
            if (bpmProcessInstanceStatusEnum.getDesc().equals(desc)) {
                return bpmProcessInstanceStatusEnum;
            }
        }
        return null;
    }

    public static boolean isRejectStatus(Integer status) {
        return REJECT.getValue().equals(status);
    }

    public static boolean isProcessEndStatus(Integer status) {
        return Arrays.asList(APPROVE.getValue(), REJECT.getValue(), CANCEL.getValue()).contains(status);
    }

}
