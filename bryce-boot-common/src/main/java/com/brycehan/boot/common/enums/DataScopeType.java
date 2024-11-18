package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 数据范围类型
 *
 * @since 2022/5/8
 * @author Bryce Han
 */
@Getter
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
    @JsonValue
    private final Integer value;

    DataScopeType(Integer value) {
        this.value = value;
    }
}
