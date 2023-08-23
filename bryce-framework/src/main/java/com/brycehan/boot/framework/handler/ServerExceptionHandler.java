package com.brycehan.boot.framework.handler;

import com.brycehan.boot.common.base.http.HttpResponseStatusEnum;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.exception.file.ExcelExportException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 服务器异常处理
 *
 * @author Bryce Han
 * @since 2022/5/6
 */
@Slf4j
@RestControllerAdvice
public class ServerExceptionHandler {

    /**
     * 访问不允许处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult accessDeniedHandler(AccessDeniedException e) {
        log.info(" 访问不允许异常，{}", e.getMessage());
        return ResponseResult.error(HttpResponseStatusEnum.HTTP_FORBIDDEN.code(), e.getMessage());
    }

    /**
     * 数据绑定异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<Void> bindException(BindException e) {
        String message = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.error("参数绑定异常", e);
        return ResponseResult.error(HttpResponseStatusEnum.HTTP_BAD_REQUEST.code(), message);
    }

    /**
     * 实体字段校验不通过异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult methodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getAllErrors()
                .stream()
                .map(item -> {
                    if (item instanceof FieldError) {
                        return "字段".concat(((FieldError) item).getField())
                                .concat(item.getDefaultMessage());
                    } else {
                        return e.getMessage();
                    }
                })
                .collect(Collectors.toList());
        log.debug("参数校验异常", e);
        return ResponseResult.error(HttpResponseStatusEnum.HTTP_BAD_REQUEST.code(), String.join(", ", errors));
    }

    /**
     * 约束违反异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        List<String> errors = violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        log.error("数据校验异常", e);
        return ResponseResult.error(HttpResponseStatusEnum.HTTP_BAD_REQUEST.code(), String.join(", ", errors));
    }

    /**
     * 请求方法不支持异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult HttpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        log.error("请求方法不支持异常", e);
        return ResponseResult.error(HttpResponseStatusEnum.HTTP_METHOD_NOT_ALLOWED, null, e.getMethod());
    }

    /**
     * 用户账户没有启用异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseResult businessException(DisabledException e) {
        log.info("用户账户没有启用异常，{}", e.getLocalizedMessage());
        return ResponseResult.error(UserResponseStatusEnum.USER_ACCOUNT_DISABLED);
    }

    /**
     * 内部认证服务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseResult internalAuthenticationServiceExceptionHandler(InternalAuthenticationServiceException e) {
        log.info("业务异常", e);
        return ResponseResult.error(UserResponseStatusEnum.USER_USERNAME_OR_PASSWORD_ERROR.code(), e.getMessage());
    }

    @ExceptionHandler(ExcelExportException.class)
    public ResponseResult<Void> businessException(ExcelExportException e) {
        log.info("业务异常", e);
        return ResponseResult.error(HttpResponseStatusEnum.HTTP_INTERNAL_ERROR.code(), e.getMessage());
    }

    /**
     * 业务异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseResult businessException(BusinessException e) {
        log.info("业务异常，{}", e);
        return ResponseResult.error(e.getCode(), e.getMessage());
    }
    /**
     * 数据key重复异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseResult duplicateKeyException(DuplicateKeyException e) {
        log.info("key重复异常，{}", e);
        return ResponseResult.error(HttpResponseStatusEnum.HTTP_BAD_REQUEST.code(), "key重复异常");
    }

    /**
     * 通用异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception e) {
        log.info("系统内部异常，{}", e);

        if (e.getCause() instanceof BusinessException businessException) {
            return ResponseResult.error(businessException);
        }

        if (Objects.nonNull(e.getCause()) && Objects.nonNull(e.getCause().getCause())) {
            // 日期时间解析异常处理
            if (e.getCause().getCause() instanceof DateTimeParseException) {
                String param = ((DateTimeParseException) e.getCause().getCause()).getParsedString();
                return ResponseResult.error(HttpResponseStatusEnum.HTTP_BAD_REQUEST.code(), "时间参数值".concat(param).concat("格式错误"));
            }
            return ResponseResult.error(HttpResponseStatusEnum.HTTP_INTERNAL_ERROR.code(), e.getCause().getCause().getMessage());
        }

        return ResponseResult.error(HttpResponseStatusEnum.HTTP_INTERNAL_ERROR.code(), e.getMessage());
    }
}
