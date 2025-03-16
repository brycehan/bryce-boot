package com.brycehan.boot.bpm.common.type;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 仿钉钉的流程器设计器的模型节点类型
 *
 * @since 2025/3/12
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum BpmSimpleModelNodeTypeEnum {

    // 0 ~ 1 开始和结束
    START_NODE(0, "开始", "startEvent"),
    END_NODE(1, "结束", "endEvent"),

    // 10 ~ 49 各种节点
    START_USER_NODE(10, "发起人", "userTask"), // 发起人节点。前端的开始节点，Id 固定
    APPROVE_NODE(11, "审批人", "userTask"),
    COPY_NODE(12, "抄送人", "serviceTask"),

    DELAY_TIMER_NODE(14, "延迟器", "receiveTask"),
    TRIGGER_NODE(15, "触发器", "serviceTask"),

    // 50 ~ 条件分支
    CONDITION_NODE(50, "条件", "sequenceFlow"), // 用于构建流转条件的表达式
    CONDITION_BRANCH_NODE(51, "条件分支", "exclusiveGateway"),
    PARALLEL_BRANCH_NODE(52, "并行分支", "parallelGateway"),
    INCLUSIVE_BRANCH_NODE(53, "包容分支", "inclusiveGateway"),
    ROUTER_BRANCH_NODE(54, "路由分支", "exclusiveGateway")
    ;

    @EnumValue
    @JsonValue
    private final Integer value;

    @DescValue
    private final String desc;

    /**
     * 流程类型
     */
    private final String bpmnType;

    /**
     * 根据值获取枚举类型
     *
     * @param value 值
     * @return 枚举类型
     */
    public static BpmSimpleModelNodeTypeEnum of(Integer value) {
        for (BpmSimpleModelNodeTypeEnum handlerTypeEnum : values()) {
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
    public static BpmSimpleModelNodeTypeEnum getByDesc(String desc) {
        for (BpmSimpleModelNodeTypeEnum handlerTypeEnum : values()) {
            if (handlerTypeEnum.getDesc().equals(desc)) {
                return handlerTypeEnum;
            }
        }
        return null;
    }

    /**
     * 判断是否为分支节点
     *
     * @param type 节点类型
     */
    public static boolean isBranchNode(Integer type) {
        return Objects.equals(CONDITION_BRANCH_NODE.getValue(), type)
                || Objects.equals(PARALLEL_BRANCH_NODE.getValue(), type)
                || Objects.equals(INCLUSIVE_BRANCH_NODE.getValue(), type)
                || Objects.equals(ROUTER_BRANCH_NODE.getValue(), type);
    }

}
