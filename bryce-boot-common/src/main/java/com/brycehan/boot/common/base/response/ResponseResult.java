package com.brycehan.boot.common.base.response;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.ServerException;
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
     * 响应类型
     */
    @Schema(description = "响应类型")
    private ResponseType type;

    /**
     * 响应时间
     */
    @Schema(description = "响应时间")
    private LocalDateTime time;

    /**
     * 响应成功
     *
     * @return 响应结果
     */
    public static <T> ResponseResult<T> ok() {
        return ok(null);
    }

    /**
     * 响应成功
     *
     * @param data 响应数据
     * @return 响应结果
     */
    public static <T> ResponseResult<T> ok(T data) {
        return ok(data, HttpResponseStatus.HTTP_OK.message());
    }

    /**
     * 响应成功
     *
     * @param data    响应数据
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> ok(T data, String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setData(data);
        responseResult.setMessage(message);
        responseResult.setType(ResponseType.SUCCESS);
        responseResult.setTime(LocalDateTime.now());
        return responseResult;
    }

    /**
     * 返回警告消息
     *
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> warn(String message) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(HttpResponseStatus.HTTP_WARN.code());
        responseResult.setMessage(message);
        responseResult.setType(ResponseType.WARN);
        return responseResult;
    }

    /**
     * 响应失败
     *
     * @return 响应结果
     */
    public static <T> ResponseResult<T> error() {
        return of(HttpResponseStatus.HTTP_INTERNAL_ERROR);
    }

    /**
     * 响应失败
     *
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> of(String message) {
        return of(HttpResponseStatus.HTTP_INTERNAL_ERROR.code(), message, HttpResponseStatus.HTTP_INTERNAL_ERROR.type());
    }

    /**
     * 响应失败
     *
     * @param code    响应编码
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> of(Integer code, String message) {
        return of(code, message, ResponseType.ERROR);
    }

    /**
     * 响应失败
     *
     * @param code    响应编码
     * @param message 响应消息
     * @return 响应结果
     */
    public static <T> ResponseResult<T> of(Integer code, String message, ResponseType type) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMessage(message);
        responseResult.setTime(LocalDateTime.now());
        responseResult.setType(type);
        return responseResult;
    }

    /**
     * 响应失败
     *
     * @param responseStatus 响应状态
     * @return 响应结果
     */
    public static <T> ResponseResult<T> of(ResponseStatus responseStatus) {
        return of(responseStatus.code(), responseStatus.message(), responseStatus.type());
    }

   /**
     * 响应失败
     *
     * @param responseStatus 响应状态
     * @param params         参数
     * @return 响应结果
     */
    public static <T> ResponseResult<T> of(ResponseStatus responseStatus, Object... params) {
        return of(responseStatus.code(), StrUtil.format(responseStatus.message(), params), responseStatus.type());
    }

    /**
     * 响应失败
     *
     * @param serverException 服务异常
     * @return 响应结果
     */
    public static <T> ResponseResult<T> of(ServerException serverException) {
        return of(serverException.getCode(), serverException.getMessage(), serverException.getType());
    }

}

