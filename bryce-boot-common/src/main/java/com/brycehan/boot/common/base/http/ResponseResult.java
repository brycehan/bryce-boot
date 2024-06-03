package com.brycehan.boot.common.base.http;

import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.util.StringFormatUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 响应结果
 *
 * @since 2021/12/31
 * @author Bryce Han
 */
@Data
@Accessors(chain = true)
@Schema(description = "响应结果")
public class ResponseResult<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 响应编码（200表示成功，其它值表示失败）
     */
    @Schema(description = "响应编码（200表示成功，其它值表示失败）")
    private Integer code = HttpResponseStatus.HTTP_OK.code();

    /**
     * 响应消息
     */
    @Schema(description = "响应消息")
    private String message = HttpResponseStatus.HTTP_OK.message();

    /**
     * 响应数据
     */
    @Schema(description = "响应数据")
    private T data;

    /**
     * 响应时间
     */
    @Schema(description = "响应时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime time;

    public static <T> ResponseResult<T> ok() {
        return ok(null);
    }

    public static <T> ResponseResult<T> ok(T data) {
        return ok(data, HttpResponseStatus.HTTP_OK.message());
    }

    public static <T> ResponseResult<T> ok(T data, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setData(data);
        responseResult.setMessage(message);
        responseResult.setTime(LocalDateTime.now());
        return responseResult;
    }

    public static <T> ResponseResult<T> error() {
        return error(HttpResponseStatus.HTTP_INTERNAL_ERROR);
    }

    public static <T> ResponseResult<T> error(String message) {
        return error(HttpResponseStatus.HTTP_INTERNAL_ERROR.code(), message);
    }

    public static <T> ResponseResult<T> error(Integer code, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setTime(LocalDateTime.now());
        return responseResult;
    }

    public static <T> ResponseResult<T> error(ResponseStatus responseStatus) {
        return error(responseStatus.code(), responseStatus.message());
    }

    public static <T> ResponseResult<T> error(ResponseStatus responseStatus, String ...params) {
        return error(responseStatus.code(), StringFormatUtils.format(responseStatus.message(), params));
    }

    public static <T> ResponseResult<T> error(ServerException serverException) {
        return error(serverException.getCode(), serverException.getMessage());
    }

}

