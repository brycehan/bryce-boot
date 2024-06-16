package com.brycehan.boot.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据状态枚举
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Getter
@RequiredArgsConstructor
public enum DataStatusType {
    /**
     * 正常
     */
    ENABLE(true),
    /**
     * 停用
     */
    DISABLE(false);

    private final Boolean value;

    /**
     * 获取值
     *
     * @return 值
     */
    @JsonValue
    public Boolean value() {
        return value;
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DataStatusType getByValue(boolean value) {
        for (DataStatusType type : values()) {
            if(value == type.value){
                return type;
            }
        }
        return null;
    }

}
