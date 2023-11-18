package com.brycehan.boot.system.api;

import com.brycehan.boot.api.system.StorageApi;
import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.framework.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 存储服务 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/17
 */
@RestController
@RequiredArgsConstructor
public class StorageApiController implements StorageApi {

    private final StorageService storageService;

    @Override
    public StorageVo upload(MultipartFile file) throws IOException {
        // 是否为空
        if(file.isEmpty()) {
            return null;
        }

        // 上传路径
        String path = this.storageService.getPath(file.getOriginalFilename());
        // 上传文件
        String url = this.storageService.upload(file.getInputStream(), path);

        // 上传信息
        StorageVo storageVo = new StorageVo();
        storageVo.setUrl(url);
        storageVo.setSize(file.getSize());

        return storageVo;
    }
}
