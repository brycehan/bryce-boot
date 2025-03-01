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

    BPM_MODEL_KEY_EXISTS(1100, "已经存在流程标识为 [{}] 的流程"),

    BPM_MODEL_NOT_EXISTS(1101, "流程模型不存在"),

    BPM_MODEL_UPDATE_NOT_MANAGER(1102, "操作流程失败，原因：你不是该流程[{}]的管理员"),
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
