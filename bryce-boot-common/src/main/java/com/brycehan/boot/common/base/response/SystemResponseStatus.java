package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 用户响应状态枚举
 * <br/>
 * Warn 警告消息状态编码在 600-999 之间
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum SystemResponseStatus implements ResponseStatus {

    ORG_LOWER_LEVEL_ORG_EXIST_CANNOT_BE_DELETED(601, "存在下级部门,不允许删除"),
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
