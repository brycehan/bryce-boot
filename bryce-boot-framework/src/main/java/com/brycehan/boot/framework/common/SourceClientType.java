package com.brycehan.boot.framework.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 请求来源客户端类型
 *
 * @author Bryce Han
 * @since 2024/4/7
 */
@Getter
@Accessors(fluent = true)
@AllArgsConstructor
public enum SourceClientType {
    PC("pc"),
    APP("app"),
    MINI_APP("miniApp"),
    ;

    private final String value;


}
