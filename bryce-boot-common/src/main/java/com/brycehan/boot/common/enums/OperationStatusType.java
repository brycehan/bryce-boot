package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Objects;

/**
 * 操作状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum OperationStatusType {

    /** 操作成功 */
    SUCCESS(1),
    /** 操作失败 */
    FAIL(0);
    /** 操作状态值 */
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
    public static OperationStatusType getByValue(Integer value) {
        for (OperationStatusType operationStatusType : values()) {
            if (Objects.equals(operationStatusType.value, value)) {
                return operationStatusType;
            }
        }
        return null;
    }

}
