package com.brycehan.boot.common.exception.file;

import com.brycehan.boot.common.base.http.UploadResponseStatus;
import com.brycehan.boot.common.exception.BusinessException;

import java.io.Serial;

/**
 * 文件大小限制异常类
 *
 * @since 2022/11/2
 * @author Bryce Han
 */
public class FileSizeLimitExceededException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultFileMaxSize) {
        super(UploadResponseStatus.UPLOAD_EXCEED_MAX_SIZE, Long.toString(defaultFileMaxSize));
    }
}
