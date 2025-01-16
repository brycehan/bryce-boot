package com.brycehan.boot.framework.common.xss;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Cross Site Scripting 跨站脚本攻击 Http 请求包装类
 *
 * @since 2022/5/26
 * @author Bryce Han
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 如果是 json 数据，则不处理
        if(StrUtil.startWithIgnoreCase(getContentType(), MediaType.APPLICATION_JSON_VALUE)) {
            return super.getInputStream();
        }

        // 读取内容，进行 xss 过滤
        String content = IoUtil.readUtf8(super.getInputStream());
        content = XssUtils.filter(content);

        // 返回新的 ServletInputStream
        final ByteArrayInputStream bis = new ByteArrayInputStream(StrUtil.bytes(content, StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return true;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }

            @Override
            public int read() {
                return bis.read();
            }
        };
    }

    @Override
    public String getQueryString() {
        return XssUtils.filter(super.getQueryString());
    }

    @Override
    public String getParameter(String name) {
        return XssUtils.filter(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtils.isEmpty(values)) {
            return values;
        }

        String[] escapeValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            escapeValues[i] = XssUtils.filter(values[i]);
        }
        return escapeValues;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = new LinkedHashMap<>();
        Map<String, String[]> parameters = super.getParameterMap();

        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);
            for (int i = 0; i < values.length; i++) {
                values[i] = XssUtils.filter(values[i]);
            }
            map.put(key, values);
        }

        return map;
    }

    @Override
    public String getHeader(String name) {
        return XssUtils.filter(super.getHeader(name));
    }

}

