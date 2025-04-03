package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Bpm 响应状态枚举
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum BpmResponseStatus implements ResponseStatus {

    // 流程模型
    MODEL_KEY_EXISTS(2_001_000, "已经存在流程标识为 [{}] 的流程", ResponseType.ERROR),
    MODEL_NOT_EXISTS(2_001_001, "流程模型不存在", ResponseType.ERROR),
    MODEL_UPDATE_NOT_MANAGER(2_001_002, "操作流程失败，原因：你不是该流程[{}]的管理员", ResponseType.ERROR),
    MODEL_FORM_NOT_CONFIG(2_001_003, "流程表单未配置，请点击[修改流程]按钮进行配置", ResponseType.ERROR),
    MODEL_FORM_NOT_EXIST(2_001_004, "动态表单不存在", ResponseType.ERROR),
    MODEL_FORM_FIELD_REPEAT(2_001_005, "表单项[{}] 和 [{}] 使用了相同的字段名[{}]", ResponseType.ERROR),
    MODEL_BPMN_START_EVENT_NOT_EXISTS(2_001_006, "BPMN 流程图中，没有开始事件", ResponseType.ERROR),
    MODEL_BPMN_USER_TASK_NAME_NOT_EXISTS(2_001_007, "BPMN 流程图中，用户任务[{}]的名称不能为空", ResponseType.ERROR),
    MODEL_TASK_CANDIDATE_NOT_CONFIG(2_001_008, "用户任务[{}]未配置审批人，请点击[流程设计]按钮，选择[任务（审批人）进行配置]", ResponseType.ERROR),

    // 流程分类
    CATEGORY_NOT_EXISTS(2_002_000, "流程分类不存在", ResponseType.ERROR),
    CATEGORY_NAME_DUPLICATE(2_002_001, "流程分类名称【{}】重复", ResponseType.ERROR),
    CATEGORY_CODE_DUPLICATE(2_002_002, "流程分类编码【{}】重复", ResponseType.ERROR),

    // 流程定义
    PROCESS_DEFINITION_KEY_NOT_MATCH(2_002_001, "流程定义的标识期望是[{}]，当前是[{}]，请修改 BPMN 流程图", ResponseType.ERROR),
    PROCESS_DEFINITION_NAME_NOT_MATCH(2_002_002, "流程定义的名称期望是[{}]，当前是[{}]，请修改 BPMN 流程图", ResponseType.ERROR),
    PROCESS_DEFINITION_NOT_EXIST(2_002_003, "流程不存在", ResponseType.ERROR),
    PROCESS_DEFINITION_IS_SUSPENDED(2_002_004, "流程处于挂起状态", ResponseType.ERROR),

    // 流程实例
    PROCESS_INSTANCE_NOT_EXISTS(2_003_000, "流程实例不存在", ResponseType.ERROR),
    PROCESS_INSTANCE_CANCEL_FAIL_NOT_EXISTS(2_003_001, "流程取消失败，流程不处于运行中", ResponseType.ERROR),
    PROCESS_INSTANCE_CANCEL_FAIL_NOT_SELF(2_003_002, "流程取消失败，该流程不是您发起的", ResponseType.ERROR),
    PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_CONFIG(2_003_003, "任务({})的候选人未配置", ResponseType.ERROR),
    PROCESS_INSTANCE_START_USER_SELECT_ASSIGNEES_NOT_EXISTS(2_003_004, "任务({})的候选人({})不存在", ResponseType.ERROR),
    PROCESS_INSTANCE_START_USER_CAN_START(2_003_005, "发起流程失败，你没有权限发起该流程", ResponseType.ERROR),
    PROCESS_INSTANCE_CANCEL_FAIL_NOT_ALLOW(2_003_006, "流程取消失败，该流程不允许取消", ResponseType.ERROR),

    // 任务
    TASK_NOT_EXISTS(2_004_000, "任务不存在", ResponseType.ERROR),
    TASK_IS_PENDING(2_004_001, "当前任务处于挂起状态，不能操作", ResponseType.ERROR),
    TASK_TARGET_NODE_NOT_EXISTS(2_004_002, "目标节点不存在", ResponseType.ERROR),
    TASK_RETURN_FAIL_SOURCE_TARGET_ERROR(2_004_003, "退回任务失败，目标节点是在并行网关上或非同一路线上，不可跳转", ResponseType.ERROR),
    TASK_DELEGATE_FAIL_USER_REPEAT(2_004_004, "任务委派失败，委派人和当前审批人为同一人", ResponseType.ERROR),
    TASK_DELEGATE_FAIL_USER_NOT_EXISTS(2_004_005, "任务委派失败，被委派人不存在", ResponseType.ERROR),
    TASK_SIGN_CREATE_USER_NOT_EXIST(2_004_006, "任务加签：选择的用户不存在", ResponseType.ERROR),
    TASK_SIGN_CREATE_TYPE_ERROR(2_004_007, "任务加签：当前任务已经{}，不能{}", ResponseType.ERROR),
    TASK_SIGN_CREATE_USER_REPEAT(2_004_008, "任务加签失败，加签人与现有审批人[{}]重复", ResponseType.ERROR),
    TASK_SIGN_DELETE_NO_PARENT(2_004_009, "任务减签失败，被减签的任务必须是通过加签生成的任务", ResponseType.ERROR),
    TASK_TRANSFER_FAIL_USER_REPEAT(2_004_010, "任务转办失败，转办人和当前审批人为同一人", ResponseType.ERROR),
    TASK_TRANSFER_FAIL_USER_NOT_EXISTS(2_004_011, "任务转办失败，转办人不存在", ResponseType.ERROR),
    TASK_OPERATE_FAIL_ASSIGN_NOT_SELF(2_004_012, "操作失败，原因：该任务的审批人不是你", ResponseType.ERROR),
    TASK_SIGNATURE_NOT_EXISTS(2_004_013, "签名不能为空", ResponseType.ERROR),
    TASK_REASON_REQUIRE(2_004_014, "审批意见不能为空", ResponseType.ERROR),

    // 用户组
    USER_GROUP_NOT_EXISTS(2_005_001, "用户组不存在", ResponseType.ERROR),
    USER_GROUP_IS_DISABLE(2_005_002, "名称为[{}]的用户组已被禁用", ResponseType.ERROR),

    // 流程监听器
    PROCESS_LISTENER_NOT_EXISTS(2_006_000, "流程监听器不存在", ResponseType.ERROR),
    PROCESS_LISTENER_CLASS_NOT_FOUND(2_006_001, "流程监听器类[{}]不存在", ResponseType.ERROR),
    PROCESS_LISTENER_CLASS_IMPLEMENTS_ERROR(2_006_002, "流程监听器类[{}]没有实现接口[{}]", ResponseType.ERROR),
    PROCESS_LISTENER_EXPRESSION_INVALID(2_006_003, "流程监听器表达式({})不合法", ResponseType.ERROR),

    // 流程表达式
    PROCESS_EXPRESSION_NOT_EXISTS(2_007_000, "流程表达式不存在", ResponseType.ERROR),
    ;

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

    /**
     * 响应类型
     */
    private final ResponseType type;
}
