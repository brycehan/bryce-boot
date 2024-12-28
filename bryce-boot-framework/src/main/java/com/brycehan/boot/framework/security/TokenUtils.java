package com.brycehan.boot.framework.security;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.constant.JwtConstants;
import com.brycehan.boot.common.enums.SourceClientType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

/**
 * 令牌工具类
 *
 * @since 2023/8/30
 * @author Bryce Han
 */
@Slf4j
public class TokenUtils {

    public static final String SOURCE_CLIENT_HEADER = "X-Source-Client";

    /**
     * 生成安全的uuid
     *
     * @return uuid
     */
    public static String uuid() {
        return UUID.randomUUID(true).toString();
    }

    /**
     * 获取请求携带的令牌
     *
     * @param request 请求request
     * @return optional令牌
     */
    public static String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(JwtConstants.TOKEN_PREFIX)) {
            return bearerToken.split(" ")[1].trim();
        }

        log.debug("请求头不含 jwt token");

        return StrUtil.EMPTY;
    }

    /**
     * 获取请求来源客户端，不存在时默认返回未知UNKNOWN
     *
     * @param request 请求request
     * @return 来源客户端
     */
    public static SourceClientType getSourceClient(HttpServletRequest request) {
        String sourceClient = request.getHeader(SOURCE_CLIENT_HEADER);

        if (StrUtil.isBlank(sourceClient)) {
            return SourceClientType.UNKNOWN;
        }

        SourceClientType sourceClientType = SourceClientType.getByValue(sourceClient);
        return Objects.requireNonNullElse(sourceClientType, SourceClientType.UNKNOWN);
    }

}
