package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import com.brycehan.boot.framework.storage.config.properties.TencentStorageProperties;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 腾讯云存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
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
    public String upload(InputStream data, String path, AccessType accessType) {
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

        return this.storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public void download(String path, String filename) {
        TencentStorageProperties tencent = storageProperties.getTencent();
        HttpServletResponse response = ServletUtils.getResponse();
        COSClient client = new COSClient(credentials, clientConfig);

        COSObject object = null;
        try {// 创建GetObjectRequest对象
            GetObjectRequest getObjectRequest = new GetObjectRequest(tencent.getBucketName(), path);
            // 获取文件对象
            object = client.getObject(getObjectRequest);
        }catch (Exception e) {
            log.error("Tencent COS 连接出错：{}", e.getMessage());
        }

        if (object == null) {
            throw new RuntimeException("下载文件不存在");
        }

        // 设置响应头
        setResponseHeaders(response, filename, (int) object.getObjectMetadata().getContentLength());

        // 将文件输出到Response
        writeToResponse(object.getObjectContent(), response);
    }
}
