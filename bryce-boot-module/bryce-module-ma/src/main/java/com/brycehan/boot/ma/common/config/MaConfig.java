package com.brycehan.boot.ma.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.Collections;

/**
 * 微信小程序配置
 *
 * @since 2023/3/17
 * @author Bryce Han
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(MaProperties.class)
public class MaConfig {

    private final MaProperties maProperties;

    /**
     * 微信小程序服务
     *
     * @return 微信小程序服务
     */
    @Bean
    public WxMaService wxMaService(){

        WxMaService wxMaService = new WxMaServiceImpl();

        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(maProperties.getAppId());
        wxMaConfig.setSecret(maProperties.getSecret());
        wxMaConfig.setToken(maProperties.getToken());
        wxMaConfig.setAesKey(maProperties.getAesKey());
        wxMaConfig.setMsgDataFormat(maProperties.getMsgDataFormat());
        wxMaService.setWxMaConfig(wxMaConfig);

        return wxMaService;
    }

    /**
     * 创建并配置微信小程序消息路由。
     *
     * @param wxMaService 微信小程序服务实例，用于消息处理和服务端接口调用。
     * @return 配置好的WxMaMessageRouter实例，用于处理不同的微信小程序消息。
     */
    @Bean
    public WxMaMessageRouter wxMaMessageRouter(WxMaService wxMaService) {
        final WxMaMessageRouter router = new WxMaMessageRouter(wxMaService);
        router.rule().handler(logHandler).next()
                .rule().async(false).content("订阅消息").handler(subscribeHandler).end()
                .rule().async(false).content("文本").handler(textHandler).end()
                .rule().async(false).content("图片").handler(picHandler).end()
                .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }

    private final WxMaMessageHandler subscribeHandler = (wxMessage, context, wxMaService, sessionManager) -> {
        wxMaService.getMsgService().sendSubscribeMsg(WxMaSubscribeMessage.builder()

                .templateId("此处更换为自已的模板ID")
                .data(Collections.singletonList(new WxMaSubscribeMessage.MsgData("keyword1", "339208499")))
                .toUser(wxMessage.getFromUser())
                .build());
        return null;
    };

    private final WxMaMessageHandler logHandler = (wxMessage, context, wxMaService, sessionManager) -> {
        log.info("收到消息：{}", wxMessage.toJson());
        wxMaService.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder()
                .content("收到信息为：" + wxMessage.toJson())
                .toUser(wxMessage.getFromUser())
                .build());
        return null;
    };

    private final WxMaMessageHandler textHandler = (wxMessage, context, wxMaService, sessionManager) -> {
        wxMaService.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder()
                .content("回复文本消息")
                .toUser(wxMessage.getFromUser())
                .build());
        return null;
    };

    private final WxMaMessageHandler picHandler = (wxMessage, context, wxMaService, sessionManager) -> {
        try {
            WxMediaUploadResult uploadResult = wxMaService.getMediaService()
                            .uploadMedia("image", "png",
                                    ClassLoader.getSystemResourceAsStream("tmp.png"));

            wxMaService.getMsgService().sendKefuMsg(WxMaKefuMessage.newImageBuilder()
                    .mediaId(uploadResult.getMediaId())
                    .toUser(wxMessage.getFromUser())
                    .build());
        } catch (WxErrorException e) {
            log.error("回复图片消息出错：{}", e.getMessage());
        }

        return null;
    };

    private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, wxMaService, sessionManager) -> {
        final File file = wxMaService.getQrcodeService().createQrcode("123", 430);
        WxMediaUploadResult uploadResult = wxMaService.getMediaService().uploadMedia("image", file);

        wxMaService.getMsgService().sendKefuMsg(WxMaKefuMessage.newImageBuilder()
                .mediaId(uploadResult.getMediaId())
                .toUser(wxMessage.getFromUser())
                .build());
        return null;
    };

}
