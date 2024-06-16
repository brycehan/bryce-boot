package com.brycehan.boot.system.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Bryce Han
 * @since 2024/3/25
 */
@Getter
@RequiredArgsConstructor
public enum ParamType {

    system("系统"),
    buildIn("内置");

    private final String value;
}
