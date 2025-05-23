package com.brycehan.boot.common.base.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 存储响应状态枚举
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum StorageResponseStatus implements ResponseStatus {

    // 存储上传
    UPLOAD_EXCEED_MAX_SIZE(5_000_000, "上传的文件大小超出限制，允许的最大大小是：{}", ResponseType.ERROR),
    UPLOAD_FILENAME_EXCEED_LENGTH(5_000_001, "上传的文件名最长{}个字符", ResponseType.ERROR),
    UPLOAD_INVALID_EXTENSION(5_000_002, "文件[{}]后缀[{}]不正确，请上传{}格式", ResponseType.ERROR),
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
