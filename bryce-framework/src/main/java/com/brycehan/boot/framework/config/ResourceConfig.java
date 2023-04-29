package com.brycehan.boot.framework.config;

import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.util.FileUploadUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 资源配置
 *
 * @author Bryce Han
 * @since 2022/11/4
 */
@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        // 本地文件访问路径映射
        registry.addResourceHandler(CommonConstants.RESOURCE_PREFIX.concat("/**"))
                .addResourceLocations("file:"
                        .concat(FileUploadUtils.DEFAULT_BASE_DIR)
                        .concat("/"));
    }
}
