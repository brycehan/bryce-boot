package com.brycehan.boot.framework.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Xss 配置属性
 *
 * @since 2023/10/26
 * @author Bryce Han
 */
@Data
@ConfigurationProperties(prefix = "bryce.xss")
public class XssProperties {
    /**
     * 是否开启 XSS
     */
    private boolean enabled;

    /**
     * 排除的URL列表
     */
    private String[] excludeUrls = {};
}
