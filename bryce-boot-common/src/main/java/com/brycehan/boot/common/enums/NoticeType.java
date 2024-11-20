package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 通知类型
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
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
    private final String desc;

    NoticeType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
