package com.brycehan.boot.framework.xss;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

/** Xss 过滤器
 *
 * @since 2023/10/26
 * @author Bryce Han
 */
@RequiredArgsConstructor
public class XssFilter extends OncePerRequestFilter {

    private final XssProperties xssProperties;

    private final PathMatcher pathMatcher;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(new XssHttpServletRequestWrapper(request), response);
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        // 放行不过滤的 url
        return Arrays.stream(xssProperties.getExcludeUrls()).anyMatch(excludeUrl -> pathMatcher.match(excludeUrl, request.getRequestURI()));
    }
}
