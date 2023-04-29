package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.util.StringFormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 响应结果
 *
 * @author Bryce Han
 * @since 2021/12/31
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
@Schema(description = "响应结果")
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应编码
     */
    @Schema(description = "响应编码")
    private Integer code;

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String message;

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 响应时间
     */
    @Schema(description = "响应时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime time;

    /**
     * 正常响应结果
     */
    public static final ResponseResult<Void> HTTP_OK = ResponseResult.ok();

    /**
     * 系统内部错误响应结果
     */
    public static final ResponseResult<Void> HTTP_ERROR = ResponseResult.error();

    public ResponseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.time = LocalDateTime.now();
    }

    public ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.time = LocalDateTime.now();
    }

    public ResponseResult(Integer code, String message, T data, String... params) {
        this.code = code;
        this.message = StringFormatUtils.format(message, params);
        this.data = data;
        this.time = LocalDateTime.now();
    }

    public static <T> ResponseResultBuilder<T> builder(){
        return new ResponseResultBuilder<>();
    }
    private static <T> ResponseResult<T> build(ResponseStatus responseStatus) {
        return new ResponseResult<>(responseStatus.code(), responseStatus.message());
    }

    private static <T> ResponseResult<T> build(ResponseStatus responseStatus, T data) {
        return new ResponseResult<>(responseStatus.code(), responseStatus.message(), data);
    }

    private static <T> ResponseResult<T> build(ResponseStatus responseStatus, T data, String... params) {
        return new ResponseResult<>(responseStatus.code(), responseStatus.message(), data, params);
    }

    private static <T> ResponseResult<T> build(Integer code, String message) {
        return new ResponseResult<>(code, message);
    }

    private static <T> ResponseResult<T> build(Integer code, String message, T data) {
        return new ResponseResult<>(code, message, data);
    }

    public static <T> ResponseResult<T> ok() {
        return build(HttpResponseStatusEnum.HTTP_OK);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return build(HttpResponseStatusEnum.HTTP_OK, data);
    }

    public static <T> ResponseResult<T> ok(T data, String message) {
        return build(HttpResponseStatusEnum.HTTP_OK.code(), message, data);
    }

    public static <T> ResponseResult<T> error() {
        return build(HttpResponseStatusEnum.HTTP_INTERNAL_ERROR);
    }

    public static <T> ResponseResult<T> error(BusinessException businessException) {
        return build(businessException.getCode(), businessException.getMessage());
    }

    public static <T> ResponseResult<T> error(ResponseStatus responseStatus) {
        return build(responseStatus);
    }

    public static <T> ResponseResult<T> error(ResponseStatus responseStatus, T data) {
        return build(responseStatus, data);
    }

    public static <T> ResponseResult<T> error(ResponseStatus responseStatus, T data, String params) {
        return build(responseStatus, data, params);
    }

    public static <T> ResponseResult<T> error(Integer value, String message) {
        return build(value, message);
    }

    public static <T> ResponseResult<T> fallback(Throwable throwable) {
        log.error("ResponseResult.fallback, e={1}", throwable);
        return new ResponseResult<>(HttpResponseStatusEnum.HTTP_SYSTEM_BUSY.code(), throwable.getMessage());
    }

    @Data
    @Accessors(fluent = true)
    @NoArgsConstructor
    public static class ResponseResultBuilder<T>{

        /**
         * 响应编码
         */
        @Schema(description = "响应编码")
        private Integer code;

        /**
         * 响应消息
         */
        @Schema(description = "响应消息")
        private String message;

        /**
         * 响应数据
         */
        @Schema(description = "响应数据")
        private T data;

        /**
         * 响应时间
         */
        @Schema(description = "响应时间")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
        private LocalDateTime time;

        public ResponseResultBuilder<T> ok(){
            this.code = HTTP_OK.code;
            this.message = HTTP_OK.message;
            return this;
        }

        public ResponseResultBuilder<T> error(){
            this.code = HTTP_ERROR.code;
            this.message = HTTP_ERROR.message;
            return this;
        }

        public ResponseResult<T> build(){
            if(Objects.isNull(this.time)){
                this.time = LocalDateTime.now();
            }
            return new ResponseResult<>(this.code, this.message, this.data, this.time);
        }
    }
}
