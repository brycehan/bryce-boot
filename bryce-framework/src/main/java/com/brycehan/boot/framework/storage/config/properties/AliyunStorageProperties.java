package com.brycehan.boot.framework.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云存储属性
 *
 * @author Bryce Han
 * @since 2023/10/1
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage.aliyun")
public class AliyunStorageProperties {

    private String endPoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
