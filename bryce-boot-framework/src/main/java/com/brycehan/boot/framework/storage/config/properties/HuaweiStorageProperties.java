package com.brycehan.boot.framework.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 华为云存储属性
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage.huawei")
public class HuaweiStorageProperties {

    private String endPoint;

    private String accessKey;

    private String secretKey;

    private String bucketName;
}
