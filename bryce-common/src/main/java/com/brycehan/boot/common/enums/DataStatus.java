package com.brycehan.boot.common.enums;

import lombok.Getter;

/**
 * 数据状态枚举
 *
 * @author Bryce Han
 * @since 2022/5/9
 */
@Getter
public enum DataStatus {
    DISABLE("0", "禁用"),

    ENABLE("1", "启用");

    private String code;
    private String text;

    DataStatus(String code, String text) {
        this.code = code;
        this.text = text;
    }

}
