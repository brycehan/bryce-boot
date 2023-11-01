package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.util.MessageUtils;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * 上传响应状态枚举
 *
 * @since 2022/5/30
 * @author Bryce Han
 */
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
