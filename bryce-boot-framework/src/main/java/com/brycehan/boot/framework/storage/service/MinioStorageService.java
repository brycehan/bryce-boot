package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.storage.config.properties.MinioStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import io.minio.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.util.Assert;

import java.io.InputStream;
import java.util.Optional;

/**
 * Minio存储服务
 *
 * @since 2023/10/2
 * @author Bryce Han
 */
@Slf4j
public class MinioStorageService extends StorageService {

    private final MinioClient minioClient;

    public MinioStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        MinioStorageProperties minio = storageProperties.getMinio();
        minioClient = MinioClient.builder()
                .endpoint(minio.getEndpoint())
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .build();

        try {
            // 查询bucketName是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minio.getBucketName())
                    .build());
            // 如果bucketName不存在，则创建
            if(!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minio.getBucketName())
                        .build());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String upload(InputStream data, String path, AccessType accessType) {
        MinioStorageProperties minio = storageProperties.getMinio();

        try {
            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(path);
            if(mediaType.isPresent()) {
                contentType = mediaType.get().toString();
            }

            // 上传文件
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minio.getBucketName())
                            .contentType(contentType)
                            .object(path)
                            .stream(data, data.available(), -1)
                    .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        }

        return minio.getEndpoint()
                .concat("/").concat(minio.getBucketName())
                .concat("/").concat(path);
    }

    @Override
    public void download(String path, String filename) {
        MinioStorageProperties minio = storageProperties.getMinio();
        HttpServletResponse response = ServletUtils.getResponse();

        GetObjectResponse object = null;
        StatObjectResponse statObjectResponse = null;
        try {// 获取对象
            object = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minio.getBucketName())
                            .object(path)
                            .build());
            // 获取对象的元数据
            statObjectResponse = minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(minio.getBucketName())
                            .object(path)
                            .build());
        } catch (Exception e) {
            log.error("MinIO 连接出错：{}", e.getMessage());
        }

        Assert.notNull(object, "文件不存在");
        Assert.notNull(statObjectResponse, "文件不存在");

        // 设置响应头
        setResponseHeaders(response, filename, (int) statObjectResponse.size());

        // 将文件输出到Response
        writeToResponse(object, response);
    }
}
