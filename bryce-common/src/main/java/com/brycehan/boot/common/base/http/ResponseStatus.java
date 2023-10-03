package com.brycehan.boot.common.base.http;

/**
 * 响应状态
 *
 * @since 2022/2/25
 * @author Bryce Han
 */
public interface ResponseStatus {

    /**
     * 获取响应编码
     *
     * @return 响应编码
     */
    Integer code();

    /**
     * 获取响应消息
     *
     * @return 响应消息
     */
    String message();

}
