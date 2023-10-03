package com.brycehan.boot.system.service.impl;

import com.brycehan.boot.system.service.SysSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @since 2022/12/6
 * @author Bryce Han
 */
@Slf4j
@Service
public class SysSMSServiceImpl implements SysSmsService {

    private final RestTemplate restTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public SysSMSServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    @Override
    public void sendSms(String phoneNumber, String message) {
        String url = "";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("tel", phoneNumber);
        params.add("content", message);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(url, httpEntity, String.class);
            log.debug("SysSMSServiceImpl.sendSms, {}", responseEntity.getBody());
        } catch (Exception e){
            log.error("SysSMSServiceImpl.sendSms, {}", e.getMessage());
            log.error("SysSMSServiceImpl.sendSms, 短信发送失败，手机号码：{}, 内容：{}", phoneNumber, message);
        }
    }
}
