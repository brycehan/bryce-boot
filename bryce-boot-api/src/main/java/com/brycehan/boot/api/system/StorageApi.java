package com.brycehan.boot.api.system;

import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.common.enums.AccessType;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    StorageVo upload(@NotNull MultipartFile file, @NotNull AccessType accessType, List<String> allowedExtensions);

    /**
     * 下载文件
     *
     * @param url           文件地址
     * @param filename      文件名
     */
    void download(@NotBlank String url, @NotBlank String filename);
}
