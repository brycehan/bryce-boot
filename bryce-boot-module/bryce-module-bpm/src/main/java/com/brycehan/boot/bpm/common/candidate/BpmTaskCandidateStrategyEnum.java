package com.brycehan.boot.bpm.common.candidate;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 流程任务候选者策略枚举
 *
 * @author Bryce Han
 * @since 2025/3/4
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmTaskCandidateStrategyEnum {

    ASSIGN_EMPTY(1, "审批人为空"),
    ROLE(10, "角色"),
    DEPT_MEMBER(20, "部门的成员"), // 包括负责人
    DEPT_LEADER(21, "部门的负责人"),
    MULTI_DEPT_LEADER_MULTI(23, "连续多级部门的负责人"),
    POST(22, "岗位"),
    USER(30, "用户"),
    START_USER_SELECT(35, "发起人自选"), // 申请人自己，可在提交申请时选择此节点的审批人
    START_USER(36, "发起人自己"), // 申请人自己, 一般紧挨开始节点，常用于发起人信息审核场景
    START_USER_DEPT_LEADER(37, "发起人部门负责人"),
    START_USER_DEPT_LEADER_MULTI(38, "发起人连续多级部门的负责人"),
    USER_GROUP(40, "用户组"),
    FORM_USER(50, "表单内用户字段"),
    FORM_DEPT_LEADER(51, "表单内部门负责人"),
    EXPRESSION(60, "流程表达式"), // 表达式 ExpressionManager
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
    public static BpmTaskCandidateStrategyEnum of(Integer value) {
        for (BpmTaskCandidateStrategyEnum strategyEnum : values()) {
            if (strategyEnum.getValue().equals(value)) {
                return strategyEnum;
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
    public static BpmTaskCandidateStrategyEnum getByDesc(String desc) {
        for (BpmTaskCandidateStrategyEnum strategyEnum : values()) {
            if (strategyEnum.getDesc().equals(desc)) {
                return strategyEnum;
            }
        }
        return null;
    }
}
