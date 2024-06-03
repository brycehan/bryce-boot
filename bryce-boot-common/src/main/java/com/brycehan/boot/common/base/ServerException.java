package com.brycehan.boot.common.base;

import com.brycehan.boot.common.base.http.HttpResponseStatus;
import com.brycehan.boot.common.base.http.ResponseStatus;
import com.brycehan.boot.common.util.StringFormatUtils;
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
    private String[] messageArgs;

    public ServerException(String message) {
        super(message);
        code = HttpResponseStatus.HTTP_INTERNAL_ERROR.code();
        this.message = message;
    }

    public ServerException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public ServerException(String message, Throwable throwable) {
        super(message, throwable);
        this.code = HttpResponseStatus.HTTP_INTERNAL_ERROR.code();
        this.message = message;
    }

    public ServerException(ResponseStatus responseStatus) {
        super(responseStatus.message());
        this.code = responseStatus.code();
        this.message = responseStatus.message();
    }

    public ServerException(ResponseStatus responseStatus, String... params) {
        super(StringFormatUtils.format(responseStatus.message(), params));
        this.code = responseStatus.code();
        this.message = StringFormatUtils.format(responseStatus.message(), params);
    }

    @Override
    public String getMessage() {
        if (ArrayUtils.isNotEmpty(messageArgs)) {
            return StringFormatUtils.format(message, messageArgs);
        } else if (StringUtils.isNotEmpty(message)) {
            return message;
        }

        return super.getMessage();
    }

}
