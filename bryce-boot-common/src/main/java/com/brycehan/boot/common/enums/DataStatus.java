package com.brycehan.boot.common.enums;

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
public enum DataStatus {
    DISABLE("0", "禁用"),

    ENABLE("1", "启用");

    private final String code;
    private final String text;

}
