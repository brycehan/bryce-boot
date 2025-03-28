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

    // 短信配置
    CONFIG_ERROR(4_000_000, "短信配置错误"),

    // 短信渠道
    CHANNEL_NOT_EXISTS(4_001_000, "短信渠道不存在"),
    CHANNEL_DISABLE(4_001_001, "短信渠道不处于开启状态，不允许选择"),
    CHANNEL_HAS_CHILDREN(4_001_002, "无法删除，该短信渠道还有短信模板"),

    // 短信模板
    TEMPLATE_NOT_EXISTS(4_002_000, "短信模板不存在"),
    TEMPLATE_CODE_DUPLICATE(4_002_001, "已经存在编码为【{}】的短信模板"),
    TEMPLATE_API_ERROR(4_002_002, "短信 API 模板调用失败，原因是：{}"),
    TEMPLATE_API_AUDIT_CHECKING(4_002_003, "短信 API 模版无法使用，原因：审批中"),
    TEMPLATE_API_AUDIT_FAIL(4_002_004, "短信 API 模版无法使用，原因：审批不通过，{}"),
    TEMPLATE_API_NOT_FOUND(4_002_005, "短信 API 模版无法使用，原因：模版不存在"),

    // 短信发送
    SEND_MOBILE_NOT_EXISTS(4_003_000, "手机号不存在"),
    SEND_MOBILE_TEMPLATE_PARAM_MISS(4_003_001, "模板参数({})缺失"),
    SEND_TEMPLATE_NOT_EXISTS(4_003_002, "短信模板不存在"),
    SEND_FAIL(4_003_000, "短信发送失败"),
    SEND_NUMBER_PER_DAY_EXCEED_LIMIT(4_003_001, "短信发送次数超过限制，请明天再试"),

    // 短信验证码
    CODE_NOT_FOUND(4_004_000, "验证码不存在"),
    CODE_EXPIRED(4_004_001, "验证码已过期"),
    CODE_USED(4_004_002, "验证码已使用"),
    CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY(4_004_004, "超过每日短信发送数量"),
    CODE_SEND_TOO_FAST(4_004_005, "短信发送过于频繁"),

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
