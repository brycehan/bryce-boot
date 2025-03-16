package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM 自动去重的类型的枚举
 *
 * @author Bryce Han
 * @since 2025/3/3
 */
@Getter
@AllArgsConstructor
public enum BpmAutoApproveTypeEnum {

    NONE(0, "不自动通过"),
    APPROVE_ALL(1, "仅审批一次，后续重复的审批节点均自动通过"),
    APPROVE_SEQUENT(2, "仅针对连续审批的节点自动通过");

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
    public static BpmAutoApproveTypeEnum of(Integer value) {
        for (BpmAutoApproveTypeEnum bpmProcessInstanceStatusEnum : values()) {
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
    public static BpmAutoApproveTypeEnum getByDesc(String desc) {
        for (BpmAutoApproveTypeEnum bpmProcessInstanceStatusEnum : values()) {
            if (bpmProcessInstanceStatusEnum.getDesc().equals(desc)) {
                return bpmProcessInstanceStatusEnum;
            }
        }
        return null;
    }

}
