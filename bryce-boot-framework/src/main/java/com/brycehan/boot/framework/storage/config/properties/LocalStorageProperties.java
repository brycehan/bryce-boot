package com.brycehan.boot.framework.storage.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 本地存储属性
 *
 * @since 2023/10/1
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.storage.local")
public class LocalStorageProperties {
    /**
     * 本地存储路径
     */
    private String path;

    /**
     * 资源起始路径
     */
    private String url = "attachment";
}
