package com.brycehan.boot.system.api;

import com.brycehan.boot.api.system.StorageApi;
import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.framework.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 存储服务 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StorageApiService implements StorageApi {

    private final StorageService storageService;

    @Override
    public StorageVo upload(MultipartFile file, String moduleName) {
        // 是否为空
        if(file.isEmpty()) {
            return null;
        }
        StorageVo storageVo;

        try {
            // 上传路径
            String path = this.storageService.getPath(file.getOriginalFilename(), moduleName);
            // 上传文件
            String url = this.storageService.upload(file.getInputStream(), path);

            // 上传信息
            storageVo = new StorageVo();
            storageVo.setUrl(url);
            storageVo.setSize(file.getSize());
        } catch (Exception e) {
            log.error("上传文件失败", e);
            throw new RuntimeException("上传文件失败");
        }

        return storageVo;
    }
}
