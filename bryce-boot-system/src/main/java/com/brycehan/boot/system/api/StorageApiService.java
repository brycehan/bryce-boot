package com.brycehan.boot.system.api;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.crypto.SecureUtil;
import com.brycehan.boot.api.system.StorageApi;
import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.FileUploadUtils;
import com.brycehan.boot.common.util.MimeTypeUtils;
import com.brycehan.boot.framework.storage.service.StorageService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

/**
 * 存储服务 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class StorageApiService implements StorageApi {

    private final StorageService storageService;

    @Override
    public StorageVo upload(@NotNull MultipartFile file, @NotNull AccessType accessType) {
        return this.upload(file, accessType, MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION);
    }

    @Override
    public StorageVo upload(@NotNull MultipartFile file, @NotNull AccessType accessType, String[] allowedExtensions) {
        // 是否为空
        if (file.isEmpty()) {
            return null;
        }

        // 文件格式校验
        FileUploadUtils.assertAllowed(file, allowedExtensions);

        StorageVo storageVo;
        try {
            // 上传路径
            String path = storageService.getPath(file.getOriginalFilename(), accessType);
            // 上传文件
            String url = storageService.upload(file.getInputStream(), path, accessType);

            // 上传信息
            storageVo = new StorageVo();
            storageVo.setName(file.getOriginalFilename());
            storageVo.setPath(path);
            storageVo.setUrl(url);
            storageVo.setSuffix(FileNameUtil.getSuffix(file.getOriginalFilename()));
            storageVo.setSize(file.getSize());
            storageVo.setAccessType(accessType);
            storageVo.setHash(SecureUtil.sha256(file.getInputStream()));
            storageVo.setPlatform(storageService.getStorageType().name());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            return null;
        }

        return storageVo;
    }
}
