package com.brycehan.boot.framework.storage.config.properties;

import com.brycehan.boot.framework.storage.config.StorageType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 存储属性
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage")
public class StorageProperties {
    /** 是否开启存储 */
    private boolean enabled;

    /** 通用配置 */
    private Config config;

    /**
     * 本地配置
     */
    private LocalStorageProperties local;

    /**
     * Minio配置
     */
    private MinioStorageProperties minio;

    /**
     * 阿里云配置
     */
    private AliyunStorageProperties aliyun;

    /**
     * 七牛云配置
     */
    private QiniuStorageProperties qiniu;

    /**
     * 华为云配置
     */
    private HuaweiStorageProperties huawei;

    /**
     * 腾讯云配置
     */
    private TencentStorageProperties tencent;

    @Data
    public static class Config {
        /** 访问域名 */
        private String domain;
        /** 配置路径前缀 */
        private String prefix;
        /** 存储类型 */
        private StorageType type;
    }

}
