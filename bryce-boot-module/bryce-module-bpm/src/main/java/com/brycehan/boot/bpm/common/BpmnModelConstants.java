package com.brycehan.boot.bpm.common;

/**
 * @author Bryce Han
 * @since 2025/3/4
 */
@SuppressWarnings("all")
public class BpmnModelConstants {

    public static final String BPMN_FILE_EXTENSION = ".bpmn20.xml";

    /**
     * BPMN 中的命名空间
     */
    public static final String NAMESPACE = "http://flowable.org/bpmn";

    /**
     * BPMN UserTask 的扩展属性，用于标记候选人策略
     */
    public static final String USER_TASK_CANDIDATE_STRATEGY = "candidateStrategy";
    /**
     * BPMN UserTask 的扩展属性，用于标记候选人参数
     */
    public static final String USER_TASK_CANDIDATE_PARAM = "candidateParam";

    /**
     * BPMN ExtensionElement 的扩展属性，用于标记边界事件类型
     */
    public static final String BOUNDARY_EVENT_TYPE = "boundaryEventType";

    /**
     * BPMN ExtensionElement 的扩展属性，用于标记用户任务超时执行动作
     */
    public static final String USER_TASK_TIMEOUT_HANDLER_TYPE = "timeoutHandlerType";

    /**
     * BPMN ExtensionElement 的扩展属性，用于标记用户任务的审批人与发起人相同时，对应的处理类型
     */
    public static final String USER_TASK_ASSIGN_START_USER_HANDLER_TYPE = "assignStartUserHandlerType";

    /**
     * BPMN ExtensionElement 的扩展属性，用于标记用户任务的空处理类型
     */
    public static final String USER_TASK_ASSIGN_EMPTY_HANDLER_TYPE = "assignEmptyHandlerType";
    /**
     * BPMN ExtensionElement 的扩展属性，用于标记用户任务的空处理的指定用户编号数组
     */
    public static final String USER_TASK_ASSIGN_USER_IDS = "assignEmptyUserIds";

    /**
     * BPMN ExtensionElement 的扩展属性，用于标记用户任务拒绝处理类型
     */
    public static final String USER_TASK_REJECT_HANDLER_TYPE = "rejectHandlerType";
    /**
     * BPMN ExtensionElement 的扩展属性，用于标记用户任务拒绝后的退回的任务 Id
     */
    public static final String USER_TASK_REJECT_RETURN_TASK_ID = "rejectReturnTaskId";

    /**
     * BPMN UserTask 的扩展属性，用于标记用户任务的审批类型
     */
    public static final String USER_TASK_APPROVE_TYPE = "approveType";

    /**
     * BPMN UserTask 的扩展属性，用于标记用户任务的审批方式
     */
    public static final String USER_TASK_APPROVE_METHOD = "approveMethod";

    /**
     * BPMN ExtensionElement 流程表单字段权限元素, 用于标记字段权限
     */
    public static final String FORM_FIELD_PERMISSION_ELEMENT = "fieldsPermission";

    /**
     * BPMN ExtensionElement Attribute, 用于标记表单字段
     */
    public static final String FORM_FIELD_PERMISSION_ELEMENT_FIELD_ATTRIBUTE = "field";
    /**
     * BPMN ExtensionElement Attribute, 用于标记表单权限
     */
    public static final String FORM_FIELD_PERMISSION_ELEMENT_PERMISSION_ATTRIBUTE = "permission";

    /**
     * BPMN ExtensionElement 操作按钮设置元素, 用于审批节点操作按钮设置
     */
    public static final String BUTTON_SETTING_ELEMENT = "buttonsSetting";

    /**
     * BPMN ExtensionElement Attribute, 用于标记按钮编号
     */
    public static final String BUTTON_SETTING_ELEMENT_ID_ATTRIBUTE = "id";

    /**
     * BPMN ExtensionElement Attribute, 用于标记按钮显示名称
     */
    public static final String BUTTON_SETTING_ELEMENT_DISPLAY_NAME_ATTRIBUTE = "displayName";

    /**
     * BPMN ExtensionElement Attribute, 用于标记按钮是否启用
     */
    public static final String BUTTON_SETTING_ELEMENT_ENABLE_ATTRIBUTE = "enable";

    /**
     * BPMN ExtensionElement 的扩展属性，用于标记触发器的类型
     */
    public static final String TRIGGER_TYPE = "triggerType";
    /**
     * BPMN ExtensionElement 的扩展属性，用于标记触发器参数
     */
    public static final String TRIGGER_PARAM = "triggerParam";

    /**
     * BPMN Start Event Node Id
     */
    public static final String START_EVENT_NODE_ID = "StartEvent";

    /**
     * 发起人节点 ID
     */
    public static final String START_USER_NODE_ID = "StartUserNode";

    /**
     * 是否需要签名
     */
    public static final String SIGN_ENABLE = "signEnable";

    /**
     * 审批意见是否必填
     */
    public static final String REASON_REQUIRE = "reasonRequire";
}
