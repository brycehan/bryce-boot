package com.brycehan.boot.framework.storage.config;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.framework.storage.config.properties.LocalStorageProperties;
import com.brycehan.boot.framework.storage.config.properties.StorageProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * 资源配置
 *
 * @since 2022/11/4
 * @author Bryce Han
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(LocalStorageProperties.class)
@ConditionalOnProperty(name = "bryce.storage.enabled", havingValue = "true")
public class LocalResourceConfig implements WebMvcConfigurer {

    private final StorageProperties storageProperties;

    private final LocalStorageProperties localStorageProperties;

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 如果不是本地存储，则返回
        if(storageProperties.getConfig().getType() != StorageType.LOCAL) {
            return;
        }

        // 获取公共文件URL访问前缀
        String publicPrefix = storageProperties.getConfig().getPublicPrefix();
        if (StrUtil.isNotEmpty(publicPrefix)) {
            publicPrefix = "/".concat(publicPrefix);
        } else {
            publicPrefix = "";
        }

        // 获取公共文件file协议访问前缀
        String accessPrefix = storageProperties.getConfig().getAccessPrefix(AccessType.PUBLIC);
        if (StrUtil.isNotEmpty(accessPrefix)) {
            accessPrefix = File.separator.concat(accessPrefix);
        } else {
            accessPrefix = File.separator;
        }

        // 本地文件访问路径映射
        registry.addResourceHandler("/".concat(localStorageProperties.getPrefix()).concat(publicPrefix).concat("/**"))
                .addResourceLocations("file:".concat(localStorageProperties.getDirectory()).concat(accessPrefix));
    }
}
