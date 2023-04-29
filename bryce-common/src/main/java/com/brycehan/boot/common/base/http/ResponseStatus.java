package com.brycehan.boot.common.base.http;

/**
 * 响应状态
 *
 * @author Bryce Han
 * @since 2022/2/25
 */
public interface ResponseStatus {

    /**
     * 获取响应编码
     *
     * @return
     */
    Integer code();

    /**
     * 获取响应消息
     *
     * @return
     */
    String message();

}
