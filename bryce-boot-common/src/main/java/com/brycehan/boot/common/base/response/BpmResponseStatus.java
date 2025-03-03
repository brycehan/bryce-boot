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

    BPM_MODEL_KEY_EXISTS(11_01_00, "已经存在流程标识为 [{}] 的流程"),

    BPM_MODEL_NOT_EXISTS(11_01_01, "流程模型不存在"),

    BPM_MODEL_UPDATE_NOT_MANAGER(11_01_02, "操作流程失败，原因：你不是该流程[{}]的管理员"),

    BPM_MODEL_FORM_NOT_CONFIG(11_01_03, "流程表单未配置，请点击[修改流程]按钮进行配置"),

    BPM_MODEL_FORM_NOT_EXIST(11_01_04, "动态表单不存在"),

    BPM_MODEL_START_EVENT_NOT_EXISTS(11_01_05, "BPMN 流程图中，没有开始事件"),

    BPM_MODEL_USER_TASK_NAME_NOT_EXISTS(11_01_06, "BPMN 流程图中，用户任务[{}]的名称不能为空"),

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
