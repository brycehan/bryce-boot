package com.brycehan.boot.common.entity;

import com.brycehan.boot.common.util.JsonUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础分页 DTO 数据传输对象
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
public abstract class BaseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 转换为JSON字符串
     * @return JSON字符串
     */
    public String toJson(){
        return JsonUtils.writeValueAsString(this);
    }
}
