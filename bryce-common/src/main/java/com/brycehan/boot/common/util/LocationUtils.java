package com.brycehan.boot.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取IP地址地理位置
 *
 * @author Bryce Han
 * @since 2022/9/21
 */
@Slf4j
public class LocationUtils {

    // IP地址查询
    public static final String IP_URL = "https://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "未知";

    /**
     * 获取IP地址地理位置
     *
     * @param ip IP地址
     * @return 区域 城市，例如【山东省 济南市】
     */
    public static String getLocationByIP(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }

        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ip", ip);
            paramMap.put("json", true);

            String responseData = HttpUtil.get(IP_URL, paramMap);
            log.info("IP：{}, 获取地理位置响应数据：{}", ip, responseData);

            if (StrUtil.isEmpty(responseData)) {
                log.error("IP：{}, 获取地理位置异常", ip);
                return UNKNOWN;
            }

            Location location = JsonUtils.readValue(responseData, Location.class);
            assert location != null;
            return String.format("%s %s", location.getPro(), location.getCity());
        } catch (Exception e) {
            log.error("IP：{}, 获取地理位置异常 {}", ip, e.getMessage());
        }

        return UNKNOWN;
    }

    @Data
    static class Location {
        /**
         * 省
         */
        private String pro;

        /**
         * 市
         */
        private String city;
    }
}
