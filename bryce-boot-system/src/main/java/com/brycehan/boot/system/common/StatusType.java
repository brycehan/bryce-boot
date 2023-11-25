package com.brycehan.boot.system.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 数据状态类型
 *
 * @author Bryce Han
 * @since 2023/11/23
 */
@Getter
@RequiredArgsConstructor
public enum StatusType {
    /**
     * 正常
     */
    ENABLE(true),
    /**
     * 停用
     */
    DISABLE(false);

    private final boolean value;
}
