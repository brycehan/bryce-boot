package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 状态类型
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Getter
public enum StatusType {

    ENABLE(1, "正常"),
    DISABLE(0, "停用");

    /**
     * 状态值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 描述
     */
    private final String desc;

    StatusType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
