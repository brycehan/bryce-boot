package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知类型
 *
 * @author Bryce Han
 * @since 2022/11/21
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum NoticeType {

    NOTICE(0, "通知"),
    ANNOUNCE(1, "公告"),
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
    public static NoticeType of(Integer value) {
        for (NoticeType noticeType : NoticeType.values()) {
            if (noticeType.getValue().equals(value)) {
                return noticeType;
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
    public static NoticeType getByDesc(String desc) {
        for (NoticeType noticeType : NoticeType.values()) {
            if (noticeType.getDesc().equals(desc)) {
                return noticeType;
            }
        }
        return null;
    }

}
