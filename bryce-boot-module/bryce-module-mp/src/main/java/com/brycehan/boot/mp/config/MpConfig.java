package com.brycehan.boot.mp.config;

import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 微信公众号配置
 *
 * @since 2023/7/17
 * @author Bryce Han
 */
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MpProperties.class)
public class MpConfig {

    private final MpProperties mpProperties;

    /**
     * 微信公众号服务
     *
     * @return 公众号服务
     */
    @Bean
    public WxMpService wxMpService(){

        WxMpService wxMpService = new WxMpServiceImpl();

        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
        wxMpConfigStorage.setAppId(mpProperties.getAppId());
        wxMpConfigStorage.setSecret(mpProperties.getSecret());
        wxMpConfigStorage.setToken(mpProperties.getToken());
        wxMpConfigStorage.setAesKey(mpProperties.getAesKey());

        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);

        return wxMpService;
    }

}
