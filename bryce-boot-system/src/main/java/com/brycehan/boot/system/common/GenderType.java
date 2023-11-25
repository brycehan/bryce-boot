package com.brycehan.boot.system.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 性别类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@RequiredArgsConstructor
public enum GenderType {
    /**
     * 男
     */
    MALE("M"),
    /**
     * 女
     */
    FEMALE("F");

    private final String value;

}
