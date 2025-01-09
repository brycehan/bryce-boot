package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.common.enums.AccessType;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储服务 Api
 *
 * @since 2022/1/1
 * @author Bryce Han
 */
public interface StorageApi {

    /**
     * 文件上传
     *
     * @param file MultipartFile 文件
     * @return http资源地址
     */
    StorageVo upload(@NotNull MultipartFile file, @NotNull AccessType accessType);

    /**
     * 文件上传
     *
     * @param file              MultipartFile 文件
     * @param allowedExtensions 允许上传的文件后缀
     * @return http资源地址
     */
    StorageVo upload(@NotNull MultipartFile file, @NotNull AccessType accessType, String[] allowedExtensions);
}
