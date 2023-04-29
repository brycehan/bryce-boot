package com.brycehan.boot.common.base.entity;

import com.alibaba.fastjson2.JSONObject;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础 Entity 实体
 *
 * @author Bryce Han
 * @since 2021/8/31
 */
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    String toJson() {
        return JSONObject.toJSONString(this);
    }

}
