package com.brycehan.boot.framework.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯云存储属性
 *
 * @author Bryce Han
 * @since 2023/10/1
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage.tencent")
public class TencentStorageProperties {

    private String accessKey;

    private String secretKey;

    private String region;

    private String bucketName;
}
