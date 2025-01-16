package com.brycehan.boot.framework.storage.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.AliyunStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;

/**
 * 本地存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class AliyunStorageService extends StorageService {

    private final OSS ossClient;

    public AliyunStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
        AliyunStorageProperties aliyun = this.storageProperties.getAliyun();
        // 初始化OSSClient实例。
        ossClient = new OSSClientBuilder().build(aliyun.getEndPoint(),
                aliyun.getAccessKeyId(),
                aliyun.getAccessKeySecret());
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        AliyunStorageProperties aliyun = storageProperties.getAliyun();

        try {
            ossClient.putObject(aliyun.getBucketName(), path, data);
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        } finally {
            if(ossClient != null) {
                ossClient.shutdown();
            }
        }

        return storageProperties.getConfig().getEndpoint().concat("/").concat(path);
    }

    @Override
    public void download(String path, String filename) {
        AliyunStorageProperties aliyun = storageProperties.getAliyun();
        HttpServletResponse response = ServletUtils.getResponse();

        OSSObject object = null;
        try {// 获取对象
            object = ossClient.getObject(new GetObjectRequest(aliyun.getBucketName(), path));
        } catch (Exception e) {
            log.error("Aliyun OSS 连接出错：{}", e.getMessage());
        }

        if (object == null) {
            throw new RuntimeException("下载文件不存在");
        }
        
        // 设置响应头
        setResponseHeaders(response, filename, (int) object.getResponse().getContentLength());

        // 将文件输出到Response
        writeToResponse(object.getObjectContent(), response);
    }
}
