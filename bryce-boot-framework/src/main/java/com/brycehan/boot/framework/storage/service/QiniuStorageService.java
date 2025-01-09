package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.framework.storage.config.properties.QiniuStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;

import java.io.File;
import java.io.InputStream;

/**
 * 七牛云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
public class QiniuStorageService extends StorageService {

    private final UploadManager uploadManager;

    private final String token;

    public QiniuStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        QiniuStorageProperties qiniu = storageProperties.getQiniu();

        this.uploadManager = new UploadManager(new Configuration(Region.autoRegion()));
        token = Auth.create(qiniu.getAccessKey(), qiniu.getSecretKey()).uploadToken(qiniu.getBucketName());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        try {
            Response response = this.uploadManager.put(IOUtils.toByteArray(data), path, token);
            if(!response.isOK()) {
                throw new RuntimeException(response.toString());
            }
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(path);
    }

    @Override
    public void download(String path, String filename) {

    }
}
