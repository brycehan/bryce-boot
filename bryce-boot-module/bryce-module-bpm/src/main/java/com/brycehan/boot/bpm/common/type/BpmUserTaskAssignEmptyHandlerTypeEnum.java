package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BPM 用户任务的审批人为空时，处理类型枚举
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmUserTaskAssignEmptyHandlerTypeEnum {

    APPROVE(1, "自动通过"), // 自动通过
    REJECT(2, "自动拒绝"), // 自动拒绝
    ASSIGN_USER(3, "指定人员审批"), // 指定人员审批
    ASSIGN_ADMIN(4, "转交给流程管理员"), // 转交给流程管理员
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
    public static BpmUserTaskAssignEmptyHandlerTypeEnum of(Integer value) {
        for (BpmUserTaskAssignEmptyHandlerTypeEnum handlerTypeEnum : values()) {
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
    public static BpmUserTaskAssignEmptyHandlerTypeEnum getByDesc(String desc) {
        for (BpmUserTaskAssignEmptyHandlerTypeEnum handlerTypeEnum : values()) {
            if (handlerTypeEnum.getDesc().equals(desc)) {
                return handlerTypeEnum;
            }
        }
        return null;
    }

}
