package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * BPM 用户任务的审批人与发起人相同时，处理类型枚举
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmUserTaskAssignStartUserHandlerTypeEnum {

    START_USER_AUDIT(1, "由发起人对自己审批"), // 由发起人对自己审批
    SKIP(2, "自动跳过"), // 自动跳过【参考飞书】：1）如果当前节点还有其他审批人，则交由其他审批人进行审批；2）如果当前节点没有其他审批人，则该节点自动通过
    TRANSFER_DEPT_LEADER(3, "转交给部门负责人审批"); // 转交给部门负责人审批【参考飞书】：若部门负责人为空，则自动通过

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
    public static BpmUserTaskAssignStartUserHandlerTypeEnum of(Integer value) {
        for (BpmUserTaskAssignStartUserHandlerTypeEnum handlerTypeEnum : values()) {
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
    public static BpmUserTaskAssignStartUserHandlerTypeEnum getByDesc(String desc) {
        for (BpmUserTaskAssignStartUserHandlerTypeEnum handlerTypeEnum : values()) {
            if (handlerTypeEnum.getDesc().equals(desc)) {
                return handlerTypeEnum;
            }
        }
        return null;
    }

}
