package com.brycehan.boot.common.base.response;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.brycehan.boot.common.enums.DescValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 响应类型
 *
 * @author Bryce Han
 * @since 2022/5/9
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum ResponseType {

    SUCCESS(0, "成功"),
    WARN(1, "警告"),
    ERROR(2, "错误"),
    ;

    /**
     * 状态值
     */
    @JsonValue
    @EnumValue
    private final Integer value;

    /**
     * 描述
     */
    @DescValue
    private final String desc;

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return 枚举
     */
    public static ResponseType of(Integer value) {
        for (ResponseType statusType : ResponseType.values()) {
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
    public static ResponseType getByDesc(String desc) {
        for (ResponseType statusType : ResponseType.values()) {
            if (statusType.getDesc().equals(desc)) {
                return statusType;
            }
        }
        return null;
    }

}
