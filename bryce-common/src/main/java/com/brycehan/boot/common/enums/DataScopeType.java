package com.brycehan.boot.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
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

    private final Integer value;

    public static DataScopeType getByValue(Integer value) {
        for (DataScopeType type : DataScopeType.values()) {
            if(value.equals(type.value)){
                return type;
            }
        }
        return null;
    }
}
