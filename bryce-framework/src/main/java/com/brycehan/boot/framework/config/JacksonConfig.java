package com.brycehan.boot.framework.config;

import com.brycehan.boot.framework.xss.XssJacksonSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.*;

/**
 * Jackson配置
 *
 * @author Bryce Han
 * @since 2022/5/26
 */
@AutoConfiguration(before = JacksonAutoConfiguration.class)
public class JacksonConfig {

    /**
     * 映射配置
     *
     * @return Jackson自定义配置
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer(){

        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_TIME)
                .toFormatter();

        return builder -> {
            builder.serializerByType(String.class, new XssJacksonSerializer());
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(ISO_DATE));
            builder.serializerByType(LocalTime.class, new LocalTimeSerializer(ISO_TIME));
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(ISO_DATE));
            builder.deserializerByType(LocalTime.class, new LocalTimeDeserializer(ISO_TIME));
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            builder.failOnUnknownProperties(false);
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        };
    }

}
