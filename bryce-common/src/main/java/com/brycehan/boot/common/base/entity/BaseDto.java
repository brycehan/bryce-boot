package com.brycehan.boot.common.base.entity;

import com.brycehan.boot.common.util.JsonUtils;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 基础分页 DTO 数据传输对象
 *
 * @author Bryce Han
 * @since 2021/8/31
 */
@Data
public abstract class BaseDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public String toString(){
        return JsonUtils.writeValueAsString(this);
    }

}
