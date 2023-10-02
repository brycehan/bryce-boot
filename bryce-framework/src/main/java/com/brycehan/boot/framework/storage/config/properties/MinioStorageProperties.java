package com.brycehan.boot.framework.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio存储属性
 *
 * @author Bryce Han
 * @since 2023/10/1
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage.minio")
public class MinioStorageProperties {

    private String endPoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
