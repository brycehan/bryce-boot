package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 数据状态枚举
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Getter
public enum StatusType {
    /**
     * 正常
     */
    ENABLE(1, "正常"),
    /**
     * 停用
     */
    DISABLE(0, "停用");

    @JsonValue
    @EnumValue
    private final Integer value;
    private final String desc;

    StatusType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
