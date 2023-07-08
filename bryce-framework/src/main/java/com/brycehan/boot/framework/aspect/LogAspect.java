package com.brycehan.boot.framework.aspect;

import com.brycehan.boot.common.annotation.Log;
import com.brycehan.boot.common.base.context.SpringContextHolder;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.enums.OperationStatus;
import com.brycehan.boot.common.util.HttpContextUtils;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.system.context.LoginUser;
import com.brycehan.boot.system.context.LoginUserContext;
import com.brycehan.boot.system.entity.SysOperationLog;
import com.brycehan.boot.system.service.SysOperationLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 操作日志记录处理
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    /**
     * 排除敏感属性字段
     */
    private static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    private final ThreadPoolExecutor executor;

    public LogAspect( ThreadPoolExecutor executor) {
        this.executor = executor;
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     * @param controllerLog 日志注解
     * @param jsonResult 被切入方法的返回结果
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, jsonResult, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param controllerLog 日志注解
     * @param e 异常
     */
    @AfterThrowing(pointcut = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, null, e);
    }

    /**
     * 处理日志
     *
     * @param joinPoint 连接点
     * @param controllerLog 控制器日志注解
     * @param jsonResult json结果
     * @param e 异常
     */
    protected void handleLog(final JoinPoint joinPoint, Log controllerLog, Object jsonResult, final Exception e) {
        try {
            // 获取当前的用户
            LoginUser loginUser = LoginUserContext.currentUser();
            // 操作日志
            SysOperationLog operationLog = new SysOperationLog();
            operationLog.setId(IdGenerator.generate());
            operationLog.setOperationStatus(OperationStatus.SUCCESS.value());
            // 请求的IP
            HttpServletRequest request = HttpContextUtils.getRequest();
            String ipAddress = IpUtils.getIpAddress(request);
            operationLog.setOperationIp(ipAddress);
            operationLog.setOperationUrl(StringUtils.substring(request.getRequestURI(), 0, 2048));
            // 操作用户
            operationLog.setOperationUserId(loginUser.getId());
            operationLog.setOperationUsername(loginUser.getUsername());
            if(Objects.nonNull(e)){
                operationLog.setOperationStatus(OperationStatus.FAIL.value());
                operationLog.setErrorMessage(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operationLog.setMethod(className.concat(".").concat(methodName).concat("()"));
            // 设置请求方式
            operationLog.setRequestMethod(request.getMethod());
            // 设置注解上的参数
            setControllerMethodDescription(joinPoint, controllerLog, operationLog, jsonResult);

            // 保存到数据库
            executor.execute(() -> Objects.requireNonNull(SpringContextHolder.getBean(SysOperationLogService.class)).save(operationLog));
        }catch (Exception ex){
            log.error("LogAspect.handleLog, 切面通知异常, 异常信息：{}", ex.getMessage());
        }

    }

    /**
     * 获取用于Controller层的注解中方法的描述信息
     *
     * @param joinPoint 连接点
     * @param controllerLog 控制层日志
     * @param operationLog 操作日志
     * @param jsonResult json结果
     * @throws JsonProcessingException Json处理异常
     */
    private void setControllerMethodDescription(JoinPoint joinPoint, Log controllerLog, SysOperationLog operationLog, Object jsonResult) throws JsonProcessingException {
        // 设置业务类型
        operationLog.setBusinessType(controllerLog.businessType().ordinal());
        // 设置标题
        operationLog.setTitle(controllerLog.title());
        // 设置操作员类别
        operationLog.setOperatorType(controllerLog.operatorType().ordinal());
        // 是否保存请求数据
        if(controllerLog.isSaveRequestData()){
            setRequestValue(joinPoint, operationLog);
        }
        // 是否保存响应数据
        if(controllerLog.isSaveResponseData() && Objects.nonNull(jsonResult)){
            operationLog.setJsonResult(StringUtils.substring(JsonUtils.writeValueAsString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，设置到操作日志中
     *
     * @param joinPoint 连接点
     * @param operationLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, SysOperationLog operationLog) throws JsonProcessingException {

        String requestMethod = operationLog.getRequestMethod();
        if(HttpMethod.POST.name().equals(requestMethod) || HttpMethod.PUT.name().equals(requestMethod)){
            String params = argsToString(joinPoint.getArgs());
            operationLog.setOperationParam(StringUtils.substring(params, 0, 2000));
        }else {
            Map<?, ?> paramsMap = (Map<?, ?>) HttpContextUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operationLog.setOperationParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数转json字符串
     *
     * @param args 参数数组
     * @return json字符串
     * @throws JsonProcessingException Json解析异常
     */
    private String argsToString(Object[] args) throws JsonProcessingException {
        if(ArrayUtils.isNotEmpty(args)){
            // 过滤去掉文件
            List<Object> objects = Arrays.stream(args).filter(item -> !LogAspect.isFilterObject(item)).toList();

            // 过滤去掉指定敏感的属性
            SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.serializeAllExcept(EXCLUDE_PROPERTIES);
            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("propertyFilter", propertyFilter);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writer(filterProvider).writeValueAsString(objects);
        }
        return StringUtils.EMPTY;
    }

    /**
     * 判断是否需要过滤的对象
     * @param o 对象信息
     * @return true需要过滤，否则返回false
     */
    public static boolean isFilterObject(final  Object o){
        Class<?> oClass = o.getClass();
        if(oClass.isArray()){
            return MultipartFile.class.isAssignableFrom(oClass.getComponentType());
        }else if (Collection.class.isAssignableFrom(oClass)){
            return ((Collection<?>) o).stream().anyMatch(item -> item instanceof MultipartFile);
        } else if (Map.class.isAssignableFrom(oClass)) {
            Map<?, ?> map = (Map<?, ?>) o;
            return map.entrySet().stream().anyMatch(item -> ((Map.Entry<?, ?>) item).getValue() instanceof MultipartFile);
        }
        return o instanceof MultipartFile
                || o instanceof HttpServletRequest
                || o instanceof HttpServletResponse
                || o instanceof BindResult<?>;
    }

}
