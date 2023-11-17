package com.brycehan.boot.common.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 操作状态
 *
 * @since 2022/11/21
 * @author Bryce Han
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum OperationStatus {

    /** 操作成功 */
    SUCCESS(true),
    /** 操作失败 */
    FAIL(false);
    /** 操作状态值 */

    private final boolean value;

}
