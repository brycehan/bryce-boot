package com.brycehan.boot.framework.storage.config.properties;

import com.brycehan.boot.common.enums.AccessType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

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

    public static final String publicPrefix = "public";

    public static final String securePrefix = "secure";

    /**
     * 根据存储类型获取保存路径
     *
     * @param accessType 访问类型
     * @return 保存路径
     */
    public final String getAccessPath(AccessType accessType) {
        String accessPath = path.concat(File.separator);
        if (accessType == AccessType.SECURE) {
            return accessPath.concat(securePrefix).concat(File.separator);
        }
        return accessPath.concat(publicPrefix).concat(File.separator);
    }
}
