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
public enum DataStatusType {
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
