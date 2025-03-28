package com.brycehan.boot.framework.common.config;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间转换配置
 * <br>
 * 用于Get请求参数时日期时间转换
 *
 * @author Bryce Han
 * @since 2025/3/25
 */
@Configuration
@RequiredArgsConstructor
public class DateTimeConfig implements WebMvcConfigurer {

    @Override
    @SuppressWarnings("all")
    public void addFormatters(FormatterRegistry registry) {
        // 方括号表示可选部分
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss][.SSSSSS]");

        registry.addConverter(new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(@NotNull String source) {
                if (StrUtil.isBlank(source)) {
                    return null;
                }
                return LocalDateTime.parse(source, formatter);
            }
        });
    }
}
