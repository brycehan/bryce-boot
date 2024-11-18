package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

/**
 * 数据范围类型
 *
 * @since 2022/5/8
 * @author Bryce Han
 */
@RequiredArgsConstructor
public enum DataScopeType {

    /** 全部数据 */
    ALL(1),
    /** 本机构及以下机构数据 */
    ORG_AND_CHILDREN(2),
    /** 本机构数据 */
    ORG_ONLY(3),
    /** 本人数据 */
    SELF(4),
    /** 自定义数据 */
    CUSTOM(5);

    @EnumValue
    private final Integer value;

    /**
     * 获取值
     *
     * @return 值
     */
    @JsonValue
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
    public static DataScopeType getByValue(Integer value) {
        for (DataScopeType type : values()) {
            if(Objects.equals(type.value, value)){
                return type;
            }
        }
        return null;
    }

}
