package com.brycehan.boot.system.common;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


/**
 * 参数类型枚举
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Getter
public enum ParamType {

    SYSTEM(0, "系统"),
    BUILD_IN(1, "内置");

    @JsonValue
    @EnumValue
    private final Integer value;
    private final String desc;

    ParamType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
