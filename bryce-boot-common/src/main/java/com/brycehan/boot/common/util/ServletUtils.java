package com.brycehan.boot.common.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Servlet工具类
 *
 * @since 2022/9/20
 * @author Bryce Han
 */
@Slf4j
public class ServletUtils {

    /**
     * 获取ServletRequestAttributes
     *
     * @return ServletRequestAttributes实例
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes;
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return HttpServletRequest实例
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取 HttpServletResponse
     *
     * @return HttpServletResponse实例
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 将对象渲染到客户端
     *
     * @param response 响应体
     * @param t 待渲染的实例
     * @param <T> 待渲染的数据类型
     */
    public static <T> void render(HttpServletResponse response, T t) {
        renderString(response, JsonUtils.writeValueAsString(t));
    }

    /**
     * 将字符串渲染到客户端
     *
     * @param response 响应体
     * @param value 待渲染的字符串数据
     */
    public static void renderString(HttpServletResponse response, String value) {
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try {
            response.getWriter().print(value);
        } catch (IOException e) {
            log.error("ServletUtils.renderString, 异常：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
