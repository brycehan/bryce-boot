package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.framework.storage.config.properties.LocalStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class LocalStorageService extends StorageService {

    public LocalStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String upload(InputStream data, String path) {

        LocalStorageProperties local = this.storageProperties.getLocal();
        try {

            File file = new File(local.getPath().concat(File.separator).concat(path));

            // 没有目录，则自动创建目录
            File parent = file.getParentFile();
            if(parent != null && !parent.mkdirs() && !parent.isDirectory()) {
                throw new IOException("目录".concat(parent.toString()).concat("创建失败"));
            }

            FileCopyUtils.copy(data, Files.newOutputStream(file.toPath()));
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(local.getUrl())
                .concat(File.separator)
                .concat(path);
    }
}
