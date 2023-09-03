package com.brycehan.wechat.config;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Bryce Han
 * @since 2023/7/17
 */
@Configuration
public class WechatConfig {

    @Bean
    public WxMpService mpService(){
        WxMpService mpService = new WxMpServiceImpl();
//        mpService.setMultiConfigStorages();
        return mpService;
    }


}
