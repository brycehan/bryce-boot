package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.framework.storage.config.properties.HuaweiStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.obs.services.ObsClient;

import java.io.File;
import java.io.InputStream;

/**
 * 华为云存储服务
 *
 * @author Bryce Han
 * @since 2023/10/2
 */
public class HuaweiStorageService extends StorageService {

    public HuaweiStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String upload(InputStream data, String path) {
        HuaweiStorageProperties huawei = this.storageProperties.getHuawei();
        ObsClient client = new ObsClient(huawei.getAccessKey(),
                huawei.getSecretKey(),
                huawei.getEndPoint());

        try (client) {
            client.putObject(huawei.getBucketName(), path, data);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(path);
    }
}
