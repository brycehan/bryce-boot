package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 通知类型
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
@SuppressWarnings("unused")
@RequiredArgsConstructor
public enum NoticeType implements EnumType {

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
    private final String desc;

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
