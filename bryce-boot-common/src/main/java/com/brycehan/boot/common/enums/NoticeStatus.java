package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum NoticeStatus implements EnumType {

    OFF(0, "关闭"),
    ON(1, "正常"),
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
    private final String desc;

    /**
     * 根据描述获取枚举
     *
     * @param desc 描述
     * @return 枚举
     */
    public static NoticeStatus getByDesc(String desc) {
        for (NoticeStatus status : values()) {
            if (status.getDesc().equals(desc)) {
                return status;
            }
        }
        return null;
    }

}
