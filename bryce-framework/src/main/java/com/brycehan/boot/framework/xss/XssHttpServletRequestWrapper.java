package com.brycehan.boot.framework.xss;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.text.StringEscapeUtils;

/**
 * Cross Site Scripting 跨站脚本攻击Http请求包装类
 *
 * @author Bryce Han
 * @since 2022/5/26
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final HttpServletRequest httpServletRequest;


    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        this.httpServletRequest = request;
    }

    @Override
    public String getQueryString() {
        return StringEscapeUtils.escapeHtml4(super.getQueryString());
    }

    @Override
    public String getParameter(String name) {
        return StringEscapeUtils.escapeHtml4(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (ArrayUtils.isEmpty(values)) {
            return values;
        }

        String[] escapeValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            escapeValues[i] = StringEscapeUtils.escapeHtml4(values[i]);
        }
        return escapeValues;
    }

}
