package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 数据范围
 *
 * @since 2022/5/8
 * @author Bryce Han
 */
@Getter
public enum DataScope {

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

    DataScope(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
