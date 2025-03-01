package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 短信响应状态枚举
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum SmsResponseStatus implements ResponseStatus {

    SMS_SEND_FAIL(1200, "短信发送失败");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}
