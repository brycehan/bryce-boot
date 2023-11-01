package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.util.MessageUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * 权限响应状态枚举
 *
 * @since 2022/5/13
 * @author Bryce Han
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum AuthResponseStatus implements ResponseStatus {

    AUTH_ROLE_BLOCKED(800, "auth.role.blocked"),

    AUTH_NO_PERMISSION(801, "auth.no.permission"),

    AUTH_NO_CREATE_PERMISSION(802, "auth.no.create.permission"),

    AUTH_NO_UPDATE_PERMISSION(803, "auth.no.update.permission"),

    AUTH_NO_DELETE_PERMISSION(804, "auth.no.delete.permission"),

    AUTH_NO_EXPORT_PERMISSION(805, "auth.no.export.permission"),

    AUTH_NO_VIEW_PERMISSION(806, "auth.no.view.permission");

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
