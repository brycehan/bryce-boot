package com.brycehan.boot.wechat.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.impl.WxMaRedisBetterConfigImpl;
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
 * 微信配置
 *
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

    /**
     * 公众号服务
     *
     * @return 公众号服务
     */
    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();

        // 查询微信公众号列表
        LambdaQueryWrapper<WechatApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WechatApp::getType, WechatAppType.mp.name());
        queryWrapper.eq(WechatApp::getStatus, true);
        List<WechatApp> appList = this.wechatAppService.list(queryWrapper);



        if(CollUtil.isNotEmpty(appList)) {
            Map<String, WxMpConfigStorage> configMap = appList.stream().map(WechatConfig::createMp)
                    .collect(Collectors.toMap(WxMpConfigStorage::getAppId, wxMpRedisConfig -> wxMpRedisConfig));

            wxMpService.setMultiConfigStorages(configMap);
        }

        return wxMpService;
    }

    /**
     * 小程序服务
     *
     * @return 小程序服务
     */
    @Bean
    public WxMaService wxMaService() {
        WxMaService wxMaService = new WxMaServiceImpl();

        // 查询微信小程序列表
        LambdaQueryWrapper<WechatApp> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WechatApp::getType, WechatAppType.mp.name());
        queryWrapper.eq(WechatApp::getStatus, true);
        List<WechatApp> appList = this.wechatAppService.list(queryWrapper);

        if(CollUtil.isNotEmpty(appList)) {
            Map<String, WxMaConfig> configMap = appList.stream().map(WechatConfig::createMa)
                    .collect(Collectors.toMap(WxMaConfig::getAppid, wxMaConfig -> wxMaConfig));

            wxMaService.setMultiConfigs(configMap);
        }

        return wxMaService;
    }

    /**
     * 创建公众号配置存储
     *
     * @param wechatApp 微信应用
     * @return 公众号配置存储
     */
    public static WxMpConfigStorage createMp(WechatApp wechatApp) {
        WxMpRedisConfigImpl wxMpRedisConfig = new WxMpRedisConfigImpl(wxRedisOps, wechatApp.getAppId());
        wxMpRedisConfig.setAppId(wechatApp.getAppId());
        wxMpRedisConfig.setSecret(wechatApp.getAppSecret());
        wxMpRedisConfig.setToken(wechatApp.getToken());
        return wxMpRedisConfig;
    }

    /**
     * 创建小程序配置存储
     *
     * @param wechatApp 微信应用
     * @return 小程序配置存储
     */
    public static WxMaConfig createMa(WechatApp wechatApp) {
        WxMaRedisBetterConfigImpl wxMaRedisBetterConfig = new WxMaRedisBetterConfigImpl(wxRedisOps, wechatApp.getAppId());

        wxMaRedisBetterConfig.setAppid(wechatApp.getAppId());
        wxMaRedisBetterConfig.setSecret(wechatApp.getAppSecret());
        wxMaRedisBetterConfig.setToken(wechatApp.getToken());
        return wxMaRedisBetterConfig;
    }

}
