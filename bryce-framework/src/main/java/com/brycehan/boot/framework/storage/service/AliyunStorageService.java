package com.brycehan.boot.framework.storage.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.brycehan.boot.framework.storage.config.properties.AliyunStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;

import java.io.File;
import java.io.InputStream;

/**
 * 本地存储服务
 *
 * @author Bryce Han
 * @since 2023/10/2
 */
public class AliyunStorageService extends StorageService {

    public AliyunStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Override
    public String upload(InputStream data, String path) {
        AliyunStorageProperties aliyun = this.storageProperties.getAliyun();
        OSS client = new OSSClientBuilder().build(aliyun.getEndPoint(),
                aliyun.getAccessKeyId(),
                aliyun.getAccessKeySecret());
        try {
            client.putObject(aliyun.getBucketName(), path, data);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        } finally {
            if(client != null) {
                client.shutdown();
            }
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(path);
    }
}
