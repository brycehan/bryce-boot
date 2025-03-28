package com.brycehan.boot.common.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.StorageResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * 文件上传工具类
 *
 * @author Bryce Han
 * @since 2025/1/4
 */
public class FileUploadUtils {

    /**
     * 验证文件名是否allowedExtensions
     *
     * @param file 文件
     * @param allowedExtensions 允许的文件扩展名
     */
    public static void assertAllowed(MultipartFile file, List<String> allowedExtensions) {
        String originalFilename = file.getOriginalFilename();
        String extension = getExtension(file);
        if (allowedExtensions != null && !isAllowedExtension(extension, allowedExtensions)) {
            throw new ServerException(StorageResponseStatus.UPLOAD_INVALID_EXTENSION, originalFilename, extension,
                    StrUtil.join(",", allowedExtensions));
        }
    }

    /**
     * 获取文件扩展名
     *
     * @param file 文件
     * @return 文件扩展名
     */
    public static String getExtension(MultipartFile file) {
        String extension = FileUtil.getSuffix(file.getOriginalFilename());
        if (StrUtil.isBlank(extension)) {
            extension = MimeTypeUtils.getExtension(Objects.requireNonNull(file.getContentType()));
        }
        return extension;
    }

    /**
     * 判断文件扩展名是否是允许的类型
     *
     * @param extension 文件扩展名
     * @param allowedExtensions 允许的文件扩展名
     * @return 是否是允许的文件扩展名
     */
    private static boolean isAllowedExtension(String extension, List<String> allowedExtensions) {
        for (String str : allowedExtensions) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }
}
