package com.brycehan.boot.common.exception.file;

import com.brycehan.boot.common.base.http.UploadResponseStatusEnum;
import com.brycehan.boot.common.exception.BusinessException;

import java.io.Serial;
import java.util.Arrays;

/**
 * 文件上传无效扩展名异常类
 *
 * @author Bryce Han
 * @since 2022/11/2
 */
public class InvalidExtensionException extends BusinessException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidExtensionException(String filename, String extensionName, String[] allowedExtension) {
        super(UploadResponseStatusEnum.UPLOAD_INVALID_EXTENSION, filename, extensionName, Arrays.toString(allowedExtension));
    }
}
