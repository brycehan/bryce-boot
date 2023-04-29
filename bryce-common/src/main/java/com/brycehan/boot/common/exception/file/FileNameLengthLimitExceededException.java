package com.brycehan.boot.common.exception.file;

import com.brycehan.boot.common.base.http.UploadResponseStatusEnum;
import com.brycehan.boot.common.exception.BusinessException;

import java.io.Serial;

/**
 * 文件名称超长限制异常类
 *
 * @author Bryce Han
 * @since 2022/11/2
 */
public class FileNameLengthLimitExceededException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super(UploadResponseStatusEnum.UPLOAD_FILENAME_EXCEED_LENGTH, Integer.toString(defaultFileNameLength));
    }
}
