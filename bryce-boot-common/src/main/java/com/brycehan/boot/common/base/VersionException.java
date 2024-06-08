package com.brycehan.boot.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;

/**
 * 版本异常
 *
 * @since 2021/12/31
 * @author Bryce Han
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class VersionException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String message = "并发操作异常";

    public VersionException() {
        super();
        code = 999;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
