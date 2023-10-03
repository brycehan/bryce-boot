package com.brycehan.boot.common.exception;

import com.brycehan.boot.common.base.http.ResponseStatus;
import com.brycehan.boot.common.util.MessageUtils;
import com.brycehan.boot.common.util.StringFormatUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;

/**
 * 业务异常
 *
 * @since 2021/12/31
 * @author Bryce Han
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 所属模块
     */
    private String module;

    /**
     * 异常编码
     */
    private Integer code;

    /**
     * 异常消息键
     */
    private String messageKey;

    /**
     * 异常消息键对应的参数
     */
    private Object[] messageArgs;

    /**
     * 异常消息
     */
    private String message;

//    public BusinessException(String module, Integer value, String key, Object[] args) {
//        this(module, value, key, args, HttpResponseStatus.HTTP_INTERNAL_ERROR.message());
//    }

//    public BusinessException(String module, Integer value, String defaultMessage) {
//        this(module, value, null, null, defaultMessage);
//    }

    protected BusinessException(String module, ResponseStatus responseStatus) {
        super(responseStatus.message());
        this.module = module;
        this.code = responseStatus.code();
    }

    protected BusinessException(ResponseStatus responseStatus) {
        super(responseStatus.message());
        this.module = "system";
        this.code = responseStatus.code();
    }

    protected BusinessException(String key) {
        super(key);
    }

    protected BusinessException(ResponseStatus responseStatus, String... params) {
        super(StringFormatUtils.format(responseStatus.message(), params));
        this.code = responseStatus.code();
    }

    protected BusinessException(Integer value, String message) {
        super(message);
        this.code = value;
    }

    @Override
    public String getMessage() {
        if (StringUtils.isNotEmpty(this.messageKey)) {
            return MessageUtils.getMessage(this.messageKey, this.messageArgs);
        } else if (StringUtils.isNotEmpty(message)) {
            return message;
        }
        return super.getMessage();
    }

    public static BusinessException responseStatus(ResponseStatus responseStatus) {
        return new BusinessException(responseStatus);
    }

    public static BusinessException responseStatus(ResponseStatus responseStatus, String... params) {
        return new BusinessException(responseStatus, params);
    }

}
