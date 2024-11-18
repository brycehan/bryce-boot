package com.brycehan.boot.framework.common.config;

import cn.hutool.core.date.DatePattern;
import com.brycehan.boot.common.constant.DataConstants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ISO_TIME;

/**
 * Jackson配置
 *
 * @since 2022/5/26
 * @author Bryce Han
 */
@Configuration
public class JacksonConfig {

    /**
     * Jackson全局Json对象映射配置
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer(){
        return builder -> {
            // 日期时间序列化
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
            // 日期时间反序列化
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(ISO_TIME));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DataConstants.DATETIME_FORMATTER));

            // 忽略未知属性异常
            builder.failOnUnknownProperties(false);
            // 禁用时间戳序列化
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            // 反序列化时忽略枚举的空属性
            builder.postConfigurer(mapper -> mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true));
        };
    }

}
