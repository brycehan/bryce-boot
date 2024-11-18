package com.brycehan.boot.common.util;

import cn.hutool.core.date.DatePattern;
import com.brycehan.boot.common.constant.DataConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.time.format.DateTimeFormatter.ISO_TIME;

/**
 * JSON工具类
 *
 * @since 2023/5/24
 * @author Bryce Han
 */
@SuppressWarnings("unused")
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // 日期时间序列化
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DatePattern.NORM_TIME_FORMATTER));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.NORM_DATETIME_FORMATTER));
        // 日期时间反序列化
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(ISO_TIME));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DataConstants.DATETIME_FORMATTER));
        objectMapper.registerModule(javaTimeModule);

        // 忽略未知属性异常
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 禁用时间戳序列化
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 反序列化时忽略枚举的空属性
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
    }

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 对象
     * @return JSON字符串
     */
    public static String writeValueAsString(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param content JSON字符串
     * @param valueType 对象类型
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T readValue(String content, Class<T> valueType){
        if(StringUtils.isBlank(content)){
            return null;
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param src JSON字符串
     * @param valueType 对象类型
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T readValue(byte[] src, Class<T> valueType){
        if(ArrayUtils.isEmpty(src)){
            return null;
        }
        try {
            return objectMapper.readValue(src, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转换为对象
     *
     * @param content JSON字符串
     * @param valueTypeRef 对象类型引用
     * @param <T> 泛型
     * @return 对象
     */
    public static <T> T readValue(String content, TypeReference<T> valueTypeRef){
        if(StringUtils.isEmpty(content)){
            return null;
        }
        try {
            return objectMapper.readValue(content, valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换值
     *
     * @param content 内容
     * @param valueType 转换类型
     * @param <T> 泛型
     * @return 转换后的值
     */
    public static <T> T readValue(String content, JavaType valueType){
        if(StringUtils.isEmpty(content)){
            return null;
        }
        try {
            return objectMapper.readValue(content, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 转换值
     *
     * @param value 值
     * @param toValueType 转换类型
     * @param <T> 泛型
     * @return 转换后的值
     */
    public static <T> T convertValue(Object value, Class<T> toValueType){
        if(value == null){
            return null;
        }
        try {
            return objectMapper.convertValue(value, toValueType);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

}
