package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.brycehan.boot.framework.storage.config.properties.TencentStorageProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

import java.io.File;
import java.io.InputStream;

/**
 * 腾讯云存储服务
 *
 * @author Bryce Han
 * @since 2023/10/2
 */
public class TencentStorageService extends StorageService {

    private final COSCredentials credentials;

    private final ClientConfig clientConfig;

    public TencentStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        TencentStorageProperties tencent = storageProperties.getTencent();
        credentials = new BasicCOSCredentials(tencent.getAccessKey(), tencent.getSecretKey());

        clientConfig = new ClientConfig(new Region(tencent.getRegion()));
        clientConfig.setHttpProtocol(HttpProtocol.https);
    }

    @Override
    public String upload(InputStream data, String path) {
        TencentStorageProperties tencent = this.storageProperties.getTencent();
        COSClient client = new COSClient(credentials, clientConfig);
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(data.available());

            PutObjectRequest request = new PutObjectRequest(tencent.getBucketName(),
                    path, data, metadata);
            PutObjectResult result = client.putObject(request);

            if(result.getETag() == null) {
                throw new RuntimeException("上传文件失败，请检查配置信息");
            }
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        } finally {
            client.shutdown();
        }

        return this.storageProperties.getConfig().getDomain()
                .concat(File.separator)
                .concat(path);
    }
}
