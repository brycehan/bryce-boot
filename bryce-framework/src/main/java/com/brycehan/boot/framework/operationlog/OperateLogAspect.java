package com.brycehan.boot.framework.operationlog;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.LocationUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.operationlog.annotation.OperateLog;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.context.LoginUserContext;
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
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.bind.BindResult;
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
 * @author Bryce Han
 * @since 2022/11/18
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class OperateLogAspect {

    /**
     * 排除敏感属性字段
     */
    private static final String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword", "confirmPassword"};

    private final OperateLogService operateLogService;

    @Around("@annotation(operateLog)")
    public Object around(ProceedingJoinPoint joinPoint, OperateLog operateLog) throws Throwable {
        // 记录开始时间
        LocalDateTime startTime = LocalDateTime.now();

        try {
            // 执行方法
            Object result = joinPoint.proceed();

            // 处理日志
            handleLog(joinPoint, operateLog, startTime, Boolean.TRUE);

            return result;
        } catch (Throwable ex) {
            // 处理日志
            handleLog(joinPoint, operateLog, startTime, Boolean.FALSE);
            throw ex;
        }
    }

    /**
     * 处理日志
     *
     * @param joinPoint 连接点
     * @param operateLog 操作日志注解
     * @param startTime 操作开始时间
     * @param status 操作状态
     */
    private void handleLog(final ProceedingJoinPoint joinPoint, OperateLog operateLog, LocalDateTime startTime, Boolean status) {
        OperateLogDto operateLogDto = new OperateLogDto();

        Annotation[] annotations = getClassAnnotations(joinPoint);
        if(Arrays.stream(annotations).noneMatch(annotation ->
                annotation instanceof RestController || annotation instanceof Controller)) {
            log.error("@OperateLog使用错误，请在控制器层添加");
            return;
        }
        // 执行时长
        long duration = LocalDateTimeUtil.between(startTime, LocalDateTime.now()).toMillis();
        operateLogDto.setDuration((int)duration);
        // 操作用户
        LoginUser currentUser = LoginUserContext.currentUser();
        if(currentUser != null) {
            operateLogDto.setUserId(currentUser.getId());
            operateLogDto.setUsername(currentUser.getUsername());
            operateLogDto.setOrgId(currentUser.getOrgId());
            operateLogDto.setTenantId(currentUser.getTenantId());
        }

        // 操作类型
        operateLogDto.setOperatedType(operateLog.type().name());
        operateLogDto.setModuleName(operateLog.moduleName());
        operateLogDto.setName(operateLog.name());
        // 如果没有指定module值，则从Tag中读取
        if(StrUtil.isEmpty(operateLogDto.getModuleName())){
            Tag tag = getClassTagAnnotation(joinPoint);
            if(tag != null) {
                String description = tag.description();
                if(description.endsWith("API")) {
                    description = description.substring(0, description.lastIndexOf("API"));
                }

                operateLogDto.setModuleName(description);
            }
        }
        // 如果没有指定name值，则从Operation中读取
        if(StrUtil.isEmpty(operateLogDto.getName())) {
            Operation operation = getMethodOperationAnnotation(joinPoint);
            if(operation != null) {
                operateLogDto.setName(operation.summary());
            }
        }
        // 请求相关
        HttpServletRequest request = ServletUtils.getRequest();
        operateLogDto.setIp(IpUtils.getIpAddress(request));
        operateLogDto.setLocation(LocationUtils.getLocationByIP(operateLogDto.getIp()));
        operateLogDto.setUserAgent(request.getHeader(HttpHeaders.USER_AGENT));
        operateLogDto.setRequestUri(request.getRequestURI());
        operateLogDto.setRequestMethod(request.getMethod());

        operateLogDto.setRequestParam(obtainRequestParam(joinPoint));
        operateLogDto.setStatus(status);
        operateLogDto.setOperatedTime(LocalDateTime.now());

        this.operateLogService.save(operateLogDto);
    }

    private static Tag getClassTagAnnotation(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotation(Tag.class);
    }

    private static Annotation[] getClassAnnotations(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getDeclaringClass().getAnnotations();
    }

    private static Operation getMethodOperationAnnotation(ProceedingJoinPoint joinPoint) {
        return ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Operation.class);
    }

    private String obtainRequestParam(ProceedingJoinPoint joinPoint) {
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

        // 过滤掉指定敏感的属性
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
