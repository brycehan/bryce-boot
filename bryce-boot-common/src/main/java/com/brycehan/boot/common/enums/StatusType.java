package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 数据状态枚举
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Getter
@RequiredArgsConstructor
public enum StatusType {
    /**
     * 正常
     */
    ENABLE(1),
    /**
     * 停用
     */
    DISABLE(0);

    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 获取值
     *
     * @return 值
     */
    public Integer value() {
        return value;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static StatusType getByValue(Integer value) {
        for (StatusType type : values()) {
            if(Objects.equals(value, type.value)){
                return type;
            }
        }
        return null;
    }

}
