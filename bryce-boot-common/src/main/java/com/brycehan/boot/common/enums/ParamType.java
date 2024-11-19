package com.brycehan.boot.common.enums;

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

    SYSTEM(0, "系统内置"),
    APP(1, "应用");

    /**
     * 类型值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    ParamType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
