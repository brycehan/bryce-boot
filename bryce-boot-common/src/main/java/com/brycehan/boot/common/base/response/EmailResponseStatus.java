package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 电子邮箱响应状态枚举
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum EmailResponseStatus implements ResponseStatus {

    // 发送
    SEND_FAIL(3_000_00, "邮件发送失败", ResponseType.ERROR),

    // 附件
    ATTACHMENT_PARAM_ERROR(3_001_01, "邮件附件参数错误", ResponseType.ERROR),
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
