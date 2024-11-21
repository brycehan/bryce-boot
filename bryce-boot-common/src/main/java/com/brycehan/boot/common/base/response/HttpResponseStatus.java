package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 通用响应状态枚举
 *
 * @see org.springframework.http.HttpStatus
 * @since 2022/5/13
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum HttpResponseStatus implements ResponseStatus {

    HTTP_OK(200, "操作成功"),

    HTTP_BAD_REQUEST(400, "参数错误"),

    HTTP_UNAUTHORIZED(401, "您还未登录，不能访问"),

    HTTP_FORBIDDEN(403, "没有权限，禁止访问"),

    HTTP_NOT_FOUND(404, "资源不存在"),

    HTTP_METHOD_NOT_ALLOWED(405, "不支持{}类型的请求方法"),

    HTTP_CONFLICT(409, "请求与服务器端目标资源的当前状态相冲突"),

    HTTP_UNSUPPORTED_MEDIA_TYPE(415, "不支持的媒体类型"),

    HTTP_PARAM_CONTAINS_ILLEGAL_CHAR(499, "请求参数包含非法字符"),

    HTTP_INTERNAL_ERROR(500, "服务器异常，请稍后再试"),

    /**
     * 注：服务器必须支持的方法（即不会返回这个状态码的方法）只有 GET 和 HEAD
     */
    HTTP_NOT_IMPLEMENTED(501, "请求的方法不被服务器支持"),

    HTTP_SYSTEM_BUSY(599, "系统繁忙");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}
