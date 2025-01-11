package com.brycehan.boot.framework.storage.config.properties;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.framework.storage.config.StorageType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.File;

/**
 * 存储属性
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage")
@EnableConfigurationProperties({LocalStorageProperties.class, MinioStorageProperties.class, AliyunStorageProperties.class,
        TencentStorageProperties.class, HuaweiStorageProperties.class, QiniuStorageProperties.class})
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

    @Getter
    @Setter
    public static class Config {

        /**
         * 访问端点
         */
        private String endpoint;

        /**
         * 存储类型
         */
        private StorageType type;

        /**
         * 公共访问前缀
         */
        private String publicPrefix = "public";

        /**
         * 安全访问前缀
         */
        private String securePrefix = "secure";

        /**
         * 根据存储类型获取访问路径前缀
         *
         * @param accessType 访问类型
         * @return 访问路径前缀
         */
        public final String getAccessPrefix(AccessType accessType) {
            if (accessType == AccessType.SECURE && StrUtil.isNotBlank(securePrefix)) {
                return securePrefix.concat(File.separator);
            } else if (accessType == AccessType.PUBLIC && StrUtil.isNotBlank(publicPrefix)) {
                return publicPrefix.concat(File.separator);
            }
            return "";
        }
    }

}
