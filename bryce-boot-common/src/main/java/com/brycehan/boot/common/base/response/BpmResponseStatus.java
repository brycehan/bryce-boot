package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 权限响应状态枚举
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BpmResponseStatus implements ResponseStatus {

    // 流程模型
    MODEL_KEY_EXISTS(11_01_00, "已经存在流程标识为 [{}] 的流程"),
    MODEL_NOT_EXISTS(11_01_01, "流程模型不存在"),
    MODEL_UPDATE_NOT_MANAGER(11_01_02, "操作流程失败，原因：你不是该流程[{}]的管理员"),
    MODEL_FORM_NOT_CONFIG(11_01_03, "流程表单未配置，请点击[修改流程]按钮进行配置"),
    MODEL_FORM_NOT_EXIST(11_01_04, "动态表单不存在"),
    MODEL_FORM_FIELD_REPEAT(11_01_05, "表单项[{}] 和 [{}] 使用了相同的字段名[{}]"),
    MODEL_BPMN_START_EVENT_NOT_EXISTS(11_01_06, "BPMN 流程图中，没有开始事件"),
    MODEL_BPMN_USER_TASK_NAME_NOT_EXISTS(11_01_07, "BPMN 流程图中，用户任务[{}]的名称不能为空"),
    MODEL_TASK_CANDIDATE_NOT_CONFIG(11_01_08, "用户任务[{}]未配置审批人，请点击[流程设计]按钮，选择[任务（审批人）进行配置]"),

    // 流程分类
    CATEGORY_NOT_EXISTS(11_02_00, "流程分类不存在"),
    CATEGORY_NAME_DUPLICATE(11_02_01, "流程分类名称【{}】重复"),
    CATEGORY_CODE_DUPLICATE(11_02_02, "流程分类编码【{}】重复"),

    // 流程定义
    PROCESS_DEFINITION_KEY_NOT_MATCH(11_03_01, "流程定义的标识期望是[{}]，当前是[{}]，请修改 BPMN 流程图"),
    PROCESS_DEFINITION_NAME_NOT_MATCH(11_03_02, "流程定义的名称期望是[{}]，当前是[{}]，请修改 BPMN 流程图"),
    PROCESS_DEFINITION_NOT_EXIST(11_03_03, "流程不存在"),
    PROCESS_DEFINITION_IS_SUSPENDED(11_03_04, "流程处于挂起状态"),

    // 流程实例
    PROCESS_INSTANCE_NOT_EXISTS(11_04_00, "流程实例不存在"),
    PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS(11_04_01, "流程取消失败，流程不处于运行中"),
    PROCESS_INSTANCE_CANCEL_FAIL_NOT_SELF(11_04_02, "流程取消失败，该流程不是您发起的"),
    PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_CONFIG(11_04_03, "任务({})的候选人未配置"),
    PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_EXISTS(11_04_04, "任务({})的候选人({})不存在"),
    PROCESS_INSTANCE_START_USER_CAN_START(11_04_05, "发起流程失败，你没有权限发起该流程"),
    PROCESS_INSTANCE_CANCEL_FAIL_NOT_ALLOW(11_04_06, "流程取消失败，该流程不允许取消"),

    // 任务
    TASK_NOT_EXISTS(11_05_00, "任务不存在"),
    TASK_IS_PENDING(11_05_01, "当前任务处于挂起状态，不能操作"),
    TASK_TARGET_NODE_NOT_EXISTS(11_05_02, "目标节点不存在"),
    TASK_RETURN_FAIL_SOURCE_TARGET_ERROR(11_05_03, "退回任务失败，目标节点是在并行网关上或非同一路线上，不可跳转"),
    TASK_DELEGATE_FAIL_USER_REPEAT(11_05_04, "任务委派失败，委派人和当前审批人为同一人"),
    TASK_DELEGATE_FAIL_USER_NOT_EXISTS(11_05_05, "任务委派失败，被委派人不存在"),
    TASK_SIGN_CREATE_USER_NOT_EXIST(11_05_06, "任务加签：选择的用户不存在"),
    TASK_SIGN_CREATE_TYPE_ERROR(11_05_07, "任务加签：当前任务已经{}，不能{}"),
    TASK_SIGN_CREATE_USER_REPEAT(11_05_08, "任务加签失败，加签人与现有审批人[{}]重复"),
    TASK_SIGN_DELETE_NO_PARENT(11_05_09, "任务减签失败，被减签的任务必须是通过加签生成的任务"),
    TASK_TRANSFER_FAIL_USER_REPEAT(11_05_10, "任务转办失败，转办人和当前审批人为同一人"),
    TASK_TRANSFER_FAIL_USER_NOT_EXISTS(11_05_11, "任务转办失败，转办人不存在"),
    TASK_OPERATE_FAIL_ASSIGN_NOT_SELF(11_05_12, "操作失败，原因：该任务的审批人不是你"),
    TASK_SIGNATURE_NOT_EXISTS(11_05_13, "签名不能为空"),
    TASK_REASON_REQUIRE(11_05_14, "审批意见不能为空"),

    // 用户组
    USER_GROUP_NOT_EXISTS(11_06_01, "用户组不存在"),
    USER_GROUP_IS_DISABLE(11_06_02, "名字为[{}]的用户组已被禁用"),

    // 流程监听器
    PROCESS_LISTENER_NOT_EXISTS(11_07_00, "流程监听器不存在"),
    PROCESS_LISTENER_CLASS_NOT_FOUND(11_07_01, "流程监听器类[{}]不存在"),
    PROCESS_LISTENER_CLASS_IMPLEMENTS_ERROR(11_07_02, "流程监听器类[{}]没有实现接口[{}]"),
    PROCESS_LISTENER_EXPRESSION_INVALID(11_07_03, "流程监听器表达式({})不合法"),

    // 流程表达式
    PROCESS_EXPRESSION_NOT_EXISTS(11_08_00, "流程表达式不存在"),
    ;

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}
