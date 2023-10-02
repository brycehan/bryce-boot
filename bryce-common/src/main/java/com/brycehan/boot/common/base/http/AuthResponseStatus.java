package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.util.MessageUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 权限响应状态枚举
 *
 * @author Bryce Han
 * @since 2022/5/13
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum AuthResponseStatus implements ResponseStatus {

    AUTH_ROLE_BLOCKED(800, MessageUtils.getMessage("auth.role.blocked")),

    AUTH_NO_PERMISSION(801, MessageUtils.getMessage("auth.no.permission")),

    AUTH_NO_CREATE_PERMISSION(802, MessageUtils.getMessage("auth.no.create.permission")),

    AUTH_NO_UPDATE_PERMISSION(803, MessageUtils.getMessage("auth.no.update.permission")),

    AUTH_NO_DELETE_PERMISSION(804, MessageUtils.getMessage("auth.no.delete.permission")),

    AUTH_NO_EXPORT_PERMISSION(805, MessageUtils.getMessage("auth.no.export.permission")),

    AUTH_NO_VIEW_PERMISSION(806, MessageUtils.getMessage("auth.no.view.permission"));

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

}
