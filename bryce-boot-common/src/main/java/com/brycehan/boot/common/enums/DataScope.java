package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据范围
 *
 * @since 2022/5/8
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum DataScope implements EnumType {

    ALL(0, "全部数据"),
    ORG_AND_CHILDREN(1, "本机构及以下机构数据"),
    ORG_ONLY(2, "本机构数据"),
    SELF(3, "本人数据"),
    CUSTOM(4, "自定义数据");

    /**
     * 类型值
     */
    @EnumValue
    @JsonValue
    private final Integer value;

     /**
     * 描述
     */
    private final String desc;

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static DataScope getByDesc(String desc) {
        for (DataScope dataScope : DataScope.values()) {
            if (dataScope.getDesc().equals(desc)) {
                return dataScope;
            }
        }
        return null;
    }

}
