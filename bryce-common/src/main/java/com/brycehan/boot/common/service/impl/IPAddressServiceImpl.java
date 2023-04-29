package com.brycehan.boot.common.service.impl;

import com.brycehan.boot.common.property.BryceApplicationProperties;
import com.brycehan.boot.common.service.IPAddressService;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * IP地址服务
 *
 * @author Bryce Han
 * @since 2022/9/21
 */
@Slf4j
@Service
public class IPAddressServiceImpl implements IPAddressService {

    // IP地址查询
    public static final String IP_URL = "https://whois.pconline.com.cn/ipJson.jsp?ip={ip}&json=true";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    private final BryceApplicationProperties bryceApplicationProperties;

    private final RestTemplate restTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public IPAddressServiceImpl(BryceApplicationProperties bryceApplicationProperties, RestTemplateBuilder restTemplateBuilder) {
        this.bryceApplicationProperties = bryceApplicationProperties;
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * 获取IP地址区域城市
     *
     * @param ip IP地址
     * @return 区域 城市，例如【山东省 济南市】
     */
    @Override
    public String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (bryceApplicationProperties.isIpAddressEnabled()) {
            try {
                Map<String, String> uriVariables = new HashMap<>();
                uriVariables.put("ip", ip);

                ResponseEntity<String> responseEntity = restTemplate.getForEntity(IP_URL, String.class, uriVariables);
                log.info("IP：{}, 获取地理位置响应数据：{}", ip, responseEntity.getBody());
                if (responseEntity.getStatusCodeValue() == 200) {
                    Map<String, String> body = JsonUtils.objectMapper.readValue(responseEntity.getBody(), Map.class);
                    String pro = body.get("pro");
                    String city = body.get("city");
                    return String.format("%s %s", pro, city);

                } else {
                    log.error("IP：{}, 获取地理位置异常，HTTP响应状态码：{}", ip, responseEntity.getStatusCodeValue());
                    return UNKNOWN;
                }
            } catch (Exception e) {
                log.error("IP：{}, 获取地理位置异常 {}", ip, e.getMessage());
            }
        }
        return UNKNOWN;
    }
}
