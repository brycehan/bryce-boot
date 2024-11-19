package com.brycehan.boot.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 操作状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
public enum OperationStatusType {

    SUCCESS(1, "操作成功"),
    FAIL(0, "操作失败");

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

    OperationStatusType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}
