package com.brycehan.boot.common.util;

import com.brycehan.boot.common.base.context.SpringContextHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;

import jakarta.annotation.PostConstruct;


public class JsonUtils {

    public static ObjectMapper objectMapper = SpringContextHolder.getBean(ObjectMapper.class);

    /**
     * 初始化
     */
    @PostConstruct
    public static void init() {
        if (ObjectUtils.isEmpty(objectMapper)) {
            JsonUtils.objectMapper = SpringContextHolder.getBean(ObjectMapper.class);
        }
    }

}
