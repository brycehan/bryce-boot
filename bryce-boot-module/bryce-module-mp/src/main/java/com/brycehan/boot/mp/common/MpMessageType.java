package com.brycehan.boot.mp.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Bryce Han
 * @since 2024/3/28
 */
@Getter
@RequiredArgsConstructor
public enum MpMessageType {
    /**
     * 用户发来的消息
     */
    IN(true),
    /**
     * 回复消息
     */
    OUT(false);

    private final boolean value;
}
