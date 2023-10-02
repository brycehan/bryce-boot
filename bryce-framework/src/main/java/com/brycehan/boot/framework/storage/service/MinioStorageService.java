package com.brycehan.boot.framework.storage.service;

import com.brycehan.boot.framework.storage.config.properties.MinioStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;

/**
 * Minio存储服务
 *
 * @author Bryce Han
 * @since 2023/10/2
 */
public class MinioStorageService extends StorageService {

    private final MinioClient minioClient;

    public MinioStorageService(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;

        MinioStorageProperties minio = storageProperties.getMinio();
        this.minioClient = MinioClient.builder()
                .endpoint(minio.getEndPoint())
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .build();
    }

    @Override
    public String upload(InputStream data, String path) {
        MinioStorageProperties minio = this.storageProperties.getMinio();
        boolean bucketExists;

        try {
            // 如果bucketName不存在，则创建
            bucketExists = this.minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(minio.getBucketName())
                    .build());
            if(!bucketExists) {
                this.minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(minio.getBucketName())
                        .build());
            }

            String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            Optional<MediaType> mediaType = MediaTypeFactory.getMediaType(path);
            if(mediaType.isPresent()) {
                contentType = mediaType.get().toString();
            }

            this.minioClient.putObject(
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

        return minio.getEndPoint()
                .concat(File.separator)
                .concat(minio.getBucketName())
                .concat(File.separator)
                .concat(path);
    }
}
