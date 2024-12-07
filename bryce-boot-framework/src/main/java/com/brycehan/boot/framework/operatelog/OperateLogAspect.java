package com.brycehan.boot.framework.operatelog;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.enums.OperateStatus;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.common.util.LocationUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * 操作日志记录处理
 *
 * @since 2022/11/18
 * @author Bryce Han
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperateLogAspect {

    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<LocalDateTime> STARTTIME_THREADLOCAL = new NamedThreadLocal<>("StartTime");

    private final OperateLogService operateLogService;

    /**
     * 处理请求前执行
     *
     * @param joinPoint 连接点
     * @param operateLog 操作日志注解
     */
    @SuppressWarnings("unused")
    @Before(value = "@annotation(operateLog)")
    public void doBefore(JoinPoint joinPoint, OperateLog operateLog) {
        // 记录开始时间
        STARTTIME_THREADLOCAL.set(LocalDateTime.now());
    }

    /**
     * 处理完请求后记录日志
     *
     * @param joinPoint 连接点
     * @param operateLog 操作日志注解
     * @param result 返回结果
     */
    @AfterReturning(pointcut = "@annotation(operateLog)", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, OperateLog operateLog, Object result) {
        handleLog(joinPoint, operateLog, null, result);
    }

    /**
     * 拦截异常并记录日志
     *
     * @param joinPoint 连接点
     * @param operateLog 操作日志注解
     * @param ex 异常
     */
    @AfterThrowing(pointcut = "@annotation(operateLog)", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, OperateLog operateLog, Exception ex) {
        handleLog(joinPoint, operateLog, ex, null);
    }

    /**
     * 处理日志
     *
     * @param joinPoint 连接点
     * @param operateLog 操作日志注解
     * @param ex 异常
     * @param result 返回结果
     */
    private void handleLog(JoinPoint joinPoint, OperateLog operateLog, Exception ex, Object result) {

        // 校验@OperateLog的使用
        Annotation[] annotations = getClassAnnotations(joinPoint);
        if(Arrays.stream(annotations).noneMatch(annotation ->
                annotation instanceof RestController || annotation instanceof Controller)) {
            log.error("@OperateLog使用错误，请在控制器层添加");
            return;
        }

        // 操作日志DTO封装
        OperateLogDto operateLogDto = new OperateLogDto();

        // 操作用户
        LoginUser currentUser = LoginUserContext.currentUser();
        if(currentUser != null) {
            operateLogDto.setUserId(currentUser.getId());
            operateLogDto.setUsername(currentUser.getUsername());
            operateLogDto.setOrgId(currentUser.getOrgId());
            operateLogDto.setOrgName(currentUser.getOrgName());
            operateLogDto.setSourceClient(currentUser.getSourceClientType());
        }

        // 请求相关参数
        HttpServletRequest request = ServletUtils.getRequest();
        operateLogDto.setIp(IpUtils.getIp(request));
        operateLogDto.setLocation(LocationUtils.getLocationByIP(operateLogDto.getIp()));
        operateLogDto.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        operateLogDto.setRequestUri(StrUtil.subPre(request.getRequestURI(), 2000));
        operateLogDto.setRequestMethod(request.getMethod());

        // 处理注解上的参数
        setControllerMethodParams(joinPoint, operateLog, operateLogDto, result);

        // 执行时长
        long duration = LocalDateTimeUtil.between(STARTTIME_THREADLOCAL.get(), LocalDateTime.now()).toMillis();
        operateLogDto.setDuration((int)duration);

        // 异常消息
        if (ex != null) {
            operateLogDto.setErrorMessage(StrUtil.subPre(ex.getMessage(), 2000));
        }

        // 操作状态
        operateLogDto.setStatus(ex == null ? OperateStatus.SUCCESS : OperateStatus.FAIL);
        operateLogDto.setOperatedTime(LocalDateTime.now());

        // 保存操作日志
        this.operateLogService.save(operateLogDto);
    }


    /**
     * 设置操作日志参数
     *
     * @param joinPoint 连接点
     * @param operateLog 操作日志注解
     * @param operateLogDto 日志对象
     * @param result 操作结果
     */
    private static void setControllerMethodParams(JoinPoint joinPoint, OperateLog operateLog, OperateLogDto operateLogDto, Object result) {
        // 操作类型
        operateLogDto.setOperatedType(operateLog.type());

        // 设置模块名
        operateLogDto.setModuleName(operateLog.moduleName());
        // 如果没有指定模块名，则从Tag中读取
        if(StrUtil.isEmpty(operateLogDto.getModuleName())){
            Tag tag = getClassTagAnnotation(joinPoint);
            if(tag != null) {
                operateLogDto.setModuleName(tag.name());
            }
        }

        // 设置name值
        operateLogDto.setName(operateLog.name());
        // 如果没有指定name值，则从Operation中读取
        if(StrUtil.isEmpty(operateLogDto.getName())) {
            Operation operation = getMethodOperationAnnotation(joinPoint);
            if(operation != null) {
                operateLogDto.setName(operation.summary());
            }
        }

        // 请求参数
        if (operateLog.saveRequestParam()) {
            operateLogDto.setRequestParam(StrUtil.subPre(obtainRequestParam(joinPoint, operateLog), 2000));
        }

        // 响应结果
        if (operateLog.saveResponseData()) {
            operateLogDto.setJsonResult(StrUtil.subPre(JsonUtils.writeValueAsString(result), 2000));
        }
    }

    /**
     * 获取切点的方法的注解
     *
     * @param joinPoint 连接点
     * @return 类注解
     */
    private static Annotation[] getClassAnnotations(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotations();
    }

    /**
     * 获取切点的方法的Tag注解
     *
     * @param joinPoint 连接点
     * @return Tag注解
     */
    private static Tag getClassTagAnnotation(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotation(Tag.class);
    }

    /**
     * 获取切点的方法的Operation注解
     *
     * @param joinPoint 连接点
     * @return Operation注解
     */
    private static Operation getMethodOperationAnnotation(JoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Operation.class);
    }

    /**
     * 获取请求参数
     *
     * @param joinPoint 连接点
     * @return 请求参数
     */
    private static String obtainRequestParam(JoinPoint joinPoint, OperateLog operateLog) {
        // 获取参数名称和参数值
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = signature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();

        // 封装参数
        Map<String, Object> requestParam = new HashMap<>(paramNames.length);
        for (int i = 0; i < paramNames.length; i++) {
            String paramName = paramNames[i];
            Object paramValue = paramValues[i];
            requestParam.put(paramName, ignoreParam(paramValue) ? "[ignore]" : paramValue);
        }

        // 过滤掉指定的脱敏的属性
        String[] EXCLUDE_PROPERTIES = operateLog.desensitizedParamNames();
        SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.serializeAllExcept(EXCLUDE_PROPERTIES);
        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter("propertyFilter", propertyFilter);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writer(filterProvider).writeValueAsString(requestParam);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断是否需要忽略的参数
     *
     * @param param 参数
     * @return true需要忽略，否则返回false
     */
    private static boolean ignoreParam(final Object param) {
        Class<?> clazz = param.getClass();

        // 处理数组
        if(clazz.isArray()) {
            return IntStream.range(0, Array.getLength(param))
                    .anyMatch(index -> ignoreParam(Array.get(param, index)));
        }

        // 处理集合
        if(Collection.class.isAssignableFrom(clazz)) {
            return ((Collection<?>) param).stream()
                    .anyMatch(OperateLogAspect::ignoreParam);
        }

        // 处理Map
        if(Map.class.isAssignableFrom(clazz)) {
            return ignoreParam(((Map<?, ?>) param).values());
        }

        return param instanceof MultipartFile
                || param instanceof HttpServletRequest
                || param instanceof HttpServletResponse
                || param instanceof BindResult<?>;
    }

}
