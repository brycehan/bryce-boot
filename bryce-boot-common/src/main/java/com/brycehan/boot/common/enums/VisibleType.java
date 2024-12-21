package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 可见性类型
 *
 * @since 2022/5/9
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum VisibleType implements EnumType {

    SHOW(1, "显示"),
    HIDE(0, "隐藏");

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

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static VisibleType getByValue(Integer value) {
        for (VisibleType statusType : VisibleType.values()) {
            if (statusType.getValue().equals(value)) {
                return statusType;
            }
        }
        return null;
    }

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static VisibleType getByDesc(String desc) {
        for (VisibleType statusType : VisibleType.values()) {
            if (statusType.getDesc().equals(desc)) {
                return statusType;
            }
        }
        return null;
    }

}
