package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.util.MessageUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 通用响应状态枚举
 *
 * @author Bryce Han
 * @see org.springframework.http.HttpStatus
 * @since 2022/5/13
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum HttpResponseStatusEnum implements ResponseStatus {

    HTTP_OK(200, MessageUtils.message("http.ok")),

    HTTP_BAD_REQUEST(400, MessageUtils.message("http.bad.request")),

    HTTP_UNAUTHORIZED(401, MessageUtils.message("http.unauthorized")),

    HTTP_FORBIDDEN(403, MessageUtils.message("http.forbidden")),

    HTTP_NOT_FOUND(404, MessageUtils.message("http.not.found")),

    HTTP_METHOD_NOT_ALLOWED(405, MessageUtils.message("http.method.not.allowed")),

    HTTP_CONFLICT(409, MessageUtils.message("http.conflict")),

    HTTP_PARAM_CONTAINS_ILLEGAL_CHAR(499, MessageUtils.message("http.param.contains.illegal.char")),

    HTTP_UNSUPPORTED_MEDIA_TYPE(415, MessageUtils.message("http.unsupported.media.type")),

    HTTP_INTERNAL_ERROR(500, MessageUtils.message("http.internal.error")),

    /**
     * 注：服务器必须支持的方法（即不会返回这个状态码的方法）只有 GET 和 HEAD
     */
    HTTP_NOT_IMPLEMENTED(501, MessageUtils.message("http.not.implemented")),

    HTTP_SYSTEM_BUSY(599, MessageUtils.message("http.system.busy"));

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

}
