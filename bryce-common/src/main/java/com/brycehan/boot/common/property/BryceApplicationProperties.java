package com.brycehan.boot.common.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Bryce应用配置
 *
 * @author Bryce Han
 * @since 2022/9/19
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bryce.application")
public class BryceApplicationProperties {

    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;

    /**
     * 实例演示开关
     */
    private boolean demoEnabled;

    public static String getAvatarPath() {
        return "/profile".concat("/avatar");
    }
}
