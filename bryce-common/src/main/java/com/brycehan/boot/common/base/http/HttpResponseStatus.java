package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.util.MessageUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * 通用响应状态枚举
 *
 * @see org.springframework.http.HttpStatus
 * @since 2022/5/13
 * @author Bryce Han
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum HttpResponseStatus implements ResponseStatus {

    HTTP_OK(200, "http.ok"),

    HTTP_BAD_REQUEST(400, "http.bad.request"),

    HTTP_UNAUTHORIZED(401, "http.unauthorized"),

    HTTP_FORBIDDEN(403, "http.forbidden"),

    HTTP_NOT_FOUND(404, "http.not.found"),

    HTTP_METHOD_NOT_ALLOWED(405, "http.method.not.allowed"),

    HTTP_CONFLICT(409, "http.conflict"),

    HTTP_PARAM_CONTAINS_ILLEGAL_CHAR(499, "http.param.contains.illegal.char"),

    HTTP_UNSUPPORTED_MEDIA_TYPE(415, "http.unsupported.media.type"),

    HTTP_INTERNAL_ERROR(500, "http.internal.error"),

    /**
     * 注：服务器必须支持的方法（即不会返回这个状态码的方法）只有 GET 和 HEAD
     */
    HTTP_NOT_IMPLEMENTED(501, "http.not.implemented"),

    HTTP_SYSTEM_BUSY(599, "http.system.busy");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

    @Override
    public Integer code() {
        return code;
    }

    @Override
    public String message() {
        return MessageUtils.getMessage(message);
    }
}
