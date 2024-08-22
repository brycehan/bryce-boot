package com.brycehan.boot.framework.common.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Redis数据封装
 *
 * @author Bryce Han
 * @since 2024/8/21
 */
@Data
public class RedisData {
    /**
     * 数据
     */
    private Object data;
    /**
     * 过期时间
     */
    private LocalDateTime expireTime;
}
