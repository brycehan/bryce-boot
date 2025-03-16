package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 用户任务的审批类型枚举
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmUserTaskApproveTypeEnum {

    USER(1, "人工审批"), // 人工审批
    AUTO_APPROVE(2, "自动通过"), // 自动通过
    AUTO_REJECT(3, "自动拒绝"); // 自动拒绝

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
    public static BpmUserTaskApproveTypeEnum of(Integer value) {
        for (BpmUserTaskApproveTypeEnum handlerTypeEnum : values()) {
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
    public static BpmUserTaskApproveTypeEnum getByDesc(String desc) {
        for (BpmUserTaskApproveTypeEnum handlerTypeEnum : values()) {
            if (handlerTypeEnum.getDesc().equals(desc)) {
                return handlerTypeEnum;
            }
        }
        return null;
    }

}
