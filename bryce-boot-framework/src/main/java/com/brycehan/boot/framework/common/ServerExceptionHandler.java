package com.brycehan.boot.framework.common;

import cn.hutool.core.util.ReflectUtil;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.*;
import com.brycehan.boot.common.util.ServletUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxError;
import me.chanjar.weixin.common.error.WxErrorException;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 服务器异常处理
 *
 * @since 2022/5/6
 * @author Bryce Han
 */
@Slf4j
@RestControllerAdvice
public class ServerExceptionHandler {

    @Value("spring.servlet.multipart.max-request-size")
    private String maxRequestSize;

    /**
     * 数据绑定异常处理
     *
     * @param e 数据绑定异常
     * @return 响应结果
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult<Void> handleException(BindException e) {
        String message = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        log.error("数据绑定异常，{}", e.getMessage());
        return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST.code(), message);
    }

    /**
     * 访问不允许处理
     *
     * @param e 访问禁止异常
     * @return 响应结果
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseResult<Void> handleException(AccessDeniedException e) {
        log.error("没有权限，禁止访问，{}", e.getMessage());
        return ResponseResult.error(HttpResponseStatus.HTTP_FORBIDDEN);
    }

    /**
     * 密码错误处理
     *
     * @param e 密码错误异常
     * @return 响应结果
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseResult<Void> handleException(BadCredentialsException e) {
        log.error(" 密码错误异常，{}", e.getMessage());

        return ResponseResult.error(SystemResponseStatus.USER_USERNAME_OR_PASSWORD_ERROR);
    }

    /**
     * 上传文件异常
     * @param e 上传文件异常
     * @return 响应结果
     */
    @ExceptionHandler(MultipartException.class)
    public ResponseResult<Void> handleException(MultipartException e) {
        log.error("上传文件异常，{}", e.getMessage());
        if (e instanceof MaxUploadSizeExceededException) {
            return ResponseResult.error(StorageResponseStatus.UPLOAD_EXCEED_MAX_SIZE, maxRequestSize);
        }
        return ResponseResult.error(e.getMessage());
    }

    /**
     * 请求方法不支持异常处理
     *
     * @param e 请求方法不支持异常
     * @return 响应结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseResult<Void> handleException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持{}类型的方法", e.getMethod(), e);
        return ResponseResult.error(HttpResponseStatus.HTTP_METHOD_NOT_ALLOWED, e.getMethod());
    }

    /**
     * 实体字段校验不通过异常处理
     *
     * @param e 实体字段校验不通过异常
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult<Void> handleException(MethodArgumentNotValidException e) {
        List<String> errors = e.getFieldErrors().stream().map(fieldError -> getFieldCNName(fieldError) + fieldError.getDefaultMessage()).collect(Collectors.toList());
        log.error("实体字段校验不通过异常，{}", e.getMessage());
        return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST.code(), String.join("，", errors));
    }

    /**
     * 获取实体字段校验不通过异常字段的中文名
     *
     * @param fieldError 字段错误
     * @return 字段中文名
     */
    private static String getFieldCNName(FieldError fieldError) {
        Class<? extends FieldError> fieldErrorClass = fieldError.getClass();
        try {
            Field violationField = fieldErrorClass.getDeclaredField("violation");
            violationField.setAccessible(true);
            Object violation = violationField.get(fieldError);
            if (violation instanceof ConstraintViolationImpl<?> constraintViolation) {
                // 获取字段的实体类class
                Class<?> rootBeanClass = constraintViolation.getRootBeanClass();
                String field = fieldError.getField();

                // 如果不是中文请求，直接返回字段名
                if (ServletUtils.nonZh()) {
                    return field + " ";
                }

                // 获取校验出错的实体类字段
                Field[] fieldsDirectly = ReflectUtil.getFieldsDirectly(rootBeanClass, true);
                Optional<Field> errorField = Arrays.stream(fieldsDirectly).filter(f -> f.getName().equals(field)).findFirst();
                return errorField.map(f -> f.getAnnotation(Schema.class)).map(Schema::description).orElse("");
            }
        } catch (Exception ex) {
            log.error("获取实体字段校验不通过异常注解失败", ex);
        }

        return "";
    }

    /**
     * 约束违反异常处理
     *
     * @param e 约束违反异常
     * @return 响应结果
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult<Void> handleException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(violation -> getFieldCNName(violation) + violation.getMessage())
                .collect(Collectors.joining(","));
        log.error("数据校验异常，{}", e.getMessage());
        return ResponseResult.error(HttpResponseStatus.HTTP_BAD_REQUEST.code(), message);
    }

    /**
     * 获取实体字段校验不通过异常字段的中文名
     *
     * @param violation 校验错误
     * @return 字段中文名
     */
    private static String getFieldCNName(ConstraintViolation<?> violation) {
        Class<?> rootBeanClass = violation.getRootBeanClass();
        Path propertyPath = violation.getPropertyPath();
        PathImpl pathImpl =(PathImpl) propertyPath;
        NodeImpl leafNode = pathImpl.getLeafNode();
        NodeImpl parentNode = leafNode.getParent();
        if (leafNode.getKind() == ElementKind.PARAMETER && parentNode.getKind() == ElementKind.METHOD) {
            // 校验失败的方法名称
            String methodName = parentNode.getName();
            // 校验失败的参数名称
            String parameterName = leafNode.getName();

            // 如果不是中文请求，直接返回字段名
            if (ServletUtils.nonZh()) {
                return parameterName + " ";
            }

            // 获取校验失败的方法
            Method errorMethod = ReflectUtil.getMethod(rootBeanClass, methodName, parentNode.getParameterTypes().toArray(Class[]::new));
            Parameter[] parameters = errorMethod.getParameters();
            // 获取校验失败的参数的中文注释
            return Arrays.stream(parameters).filter(parameter -> parameter.getName().equals(parameterName)).findFirst()
                    .map(parameter -> parameter.getAnnotation(io.swagger.v3.oas.annotations.Parameter.class))
                    .map(io.swagger.v3.oas.annotations.Parameter::description).orElse("");
        }
        return "";
    }

    /**
     * 内部认证服务异常处理
     *
     * @param e 内部认证服务异常
     * @return 响应结果
     */
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseResult<Void> handleException(InternalAuthenticationServiceException e) {
        log.error("账号与密码不匹配，{}", e.getMessage());
        return ResponseResult.error(SystemResponseStatus.USER_USERNAME_OR_PASSWORD_ERROR.code(), e.getMessage());
    }

    /**
     * 用户账户没有启用异常处理
     *
     * @param e 用户账户没有启用异常
     * @return 响应结果
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseResult<Void> handleException(DisabledException e) {
        log.error("用户账户没有启用异常，{}", e.getLocalizedMessage());
        return ResponseResult.error(SystemResponseStatus.USER_USERNAME_DISABLED);
    }

    /**
     * 请求资源不存在异常捕获
     *
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseResult<Void> handleException(NoResourceFoundException e) {
        log.error("资源不存在：{}", e.getResourcePath());
        return ResponseResult.error(HttpResponseStatus.HTTP_NOT_FOUND);
    }

    /**
     * 微信异常处理
     *
     * @param e 微信异常
     * @return 响应结果
     */
    @ExceptionHandler(WxErrorException.class)
    public ResponseResult<Void> handleException(WxErrorException e) {
        WxError error = e.getError();
        log.error("调用微信API异常，错误码：{}，错误消息：{}", error.getErrorCode(), error.getErrorMsg());
        return ResponseResult.error(error.getErrorMsg());
    }

    /**
     * 服务器异常处理
     *
     * @param e 服务器异常
     * @return 响应结果
     */
    @ExceptionHandler(ServerException.class)
    public ResponseResult<Void> handleException(ServerException e) {
        log.error("服务器异常", e);
        return ResponseResult.error(e.getCode(), e.getMessage());
    }

    /**
     * 通用异常处理
     *
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult<Void> handleException(Exception e) {
        log.error("系统内部异常", e);
        return ResponseResult.error(HttpResponseStatus.HTTP_INTERNAL_ERROR.code(), e.getMessage());
    }

}
