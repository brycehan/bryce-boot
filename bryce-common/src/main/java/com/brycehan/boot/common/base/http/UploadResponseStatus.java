package com.brycehan.boot.common.base.http;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 上传响应状态枚举
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum UploadResponseStatus implements ResponseStatus {

    UPLOAD_EXCEED_MAX_SIZE(700, "upload.exceed.max.size"),

    UPLOAD_FILENAME_EXCEED_LENGTH(701, "upload.filename.exceed.length"),

    UPLOAD_INVALID_EXTENSION(702, "upload.invalid.extension");

    /**
     * 状态编码
     */
    private final Integer code;

    /**
     * 状态值
     */
    private final String value;

}
