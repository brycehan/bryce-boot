package com.brycehan.boot.mp.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Bryce Han
 * @since 2024/3/28
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
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
