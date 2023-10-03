package com.brycehan.boot.framework.filter;

import com.brycehan.boot.framework.xss.XssHttpServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/**
 * Cross Site Scripting 跨站脚本攻击过滤器
 *
 * @since 2022/5/26
 * @author Bryce Han
 */
@WebFilter(filterName = "xssFilter", urlPatterns = "/*", asyncSupported = true)
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(httpServletRequest);
        chain.doFilter(xssHttpServletRequestWrapper, response);
    }

}
