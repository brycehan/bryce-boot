package com.brycehan.boot.wechat.config;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.wechat.entity.WechatApp;
import com.brycehan.boot.wechat.service.WechatAppService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.redis.RedisTemplateWxRedisOps;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpRedisConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @since 2023/7/17
 * @author Bryce Han
 */
@Configuration
@RequiredArgsConstructor
public class WechatConfig {

    private final WechatAppService wechatAppService;

    private final StringRedisTemplate stringRedisTemplate;

    private static RedisTemplateWxRedisOps wxRedisOps = null;

    @PostConstruct
    void init() {
        wxRedisOps = new RedisTemplateWxRedisOps(stringRedisTemplate);
    }

    @Bean
    public WxMpService mpService(){
        WxMpService mpService = new WxMpServiceImpl();

        // 查询微信公众号列表
        LambdaQueryWrapper<WechatApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WechatApp::getType, WechatAppType.mp.name());
        queryWrapper.eq(WechatApp::getStatus, true);
        List<WechatApp> appList = this.wechatAppService.list(queryWrapper);



        if(CollUtil.isNotEmpty(appList)) {
            Map<String, WxMpConfigStorage> configMap = appList.stream().map(WechatConfig::create)
                    .collect(Collectors.toMap(WxMpConfigStorage::getAppId, wxMpRedisConfig -> wxMpRedisConfig));

            mpService.setMultiConfigStorages(configMap);
        }

        return mpService;
    }

    /**
     * 创建配置存储
     * @param wechatApp 微信应用
     * @return 配置存储
     */
    public static WxMpConfigStorage create(WechatApp wechatApp) {
        WxMpRedisConfigImpl wxMpRedisConfig = new WxMpRedisConfigImpl(wxRedisOps, wechatApp.getAppId());
        wxMpRedisConfig.setAppId(wechatApp.getAppId());
        wxMpRedisConfig.setSecret(wechatApp.getAppSecret());
        wxMpRedisConfig.setToken(wechatApp.getToken());
        return wxMpRedisConfig;
    }

}
