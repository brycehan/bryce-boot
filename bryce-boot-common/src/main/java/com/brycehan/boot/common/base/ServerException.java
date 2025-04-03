package com.brycehan.boot.common.base;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.response.HttpResponseStatus;
import com.brycehan.boot.common.base.response.ResponseStatus;
import com.brycehan.boot.common.base.response.ResponseType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;

/**
 * 业务异常
 *
 * @since 2021/12/31
 * @author Bryce Han
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class ServerException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 异常消息
     */
    private String message;

    /**
     * 异常消息键对应的参数
     */
    private Object[] messageArgs;

    /**
     * 响应类型
     */
    private ResponseType type;

    public ServerException(String message) {
        super(message);
        code = HttpResponseStatus.HTTP_INTERNAL_ERROR.code();
        this.message = message;
        type = ResponseType.ERROR;
    }

    public ServerException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        type = ResponseType.ERROR;
    }

    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
        code = HttpResponseStatus.HTTP_INTERNAL_ERROR.code();
        this.message = message;
        type = ResponseType.ERROR;
    }

    public ServerException(ResponseStatus responseStatus, Object... params) {
        super(StrUtil.format(responseStatus.message(), params));
        code = responseStatus.code();
        message = StrUtil.format(responseStatus.message(), params);
        type = responseStatus.type();
    }

    /**
     * 构造异常
     *
     * @param responseStatus 响应状态
     * @param params         参数
     * @return 异常
     */
    public static ServerException of(ResponseStatus responseStatus, Object... params) {
        return new ServerException(responseStatus, params);
    }

    @Override
    public String getMessage() {
        if (ArrayUtils.isNotEmpty(messageArgs)) {
            return StrUtil.format(message, messageArgs);
        } else if (StringUtils.isNotEmpty(message)) {
            return message;
        }

        return super.getMessage();
    }

}
