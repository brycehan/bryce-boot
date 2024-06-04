package com.brycehan.boot.mp.common.config;

import com.brycehan.boot.mp.common.handler.*;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static me.chanjar.weixin.common.api.WxConsts.EventType;
import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType.EVENT;
import static me.chanjar.weixin.mp.constant.WxMpEventConstants.CustomerService.KF_CREATE_SESSION;

/**
 * 微信消息路由配置
 *
 * @since 2023/7/17
 * @author Bryce Han
 */
@Configuration
@RequiredArgsConstructor
public class MpMessageRouterConfig {

    private final KfSessionHandler kfSessionHandler;
    private final LogHandler logHandler;
    private final MenuHandler menuHandler;
    private final MessageHandler messageHandler;
    private final NullHandler nullHandler;
    private final ScanHandler scanHandler;
    private final SubscribeHandler subscribeHandler;
    private final UnsubscribeHandler unsubscribeHandler;

    /**
     * 微信消息路由配置
     *
     * @param wxMpService 微信公众号服务
     * @return 微信消息路由
     */
    @Bean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService) {
        final WxMpMessageRouter messageRouter = new WxMpMessageRouter(wxMpService);

        // 记录所有事件的日志
        messageRouter.rule().async(false).handler(this.logHandler).next();
        // 接收客服会话管理事件
        messageRouter.rule().async(false).msgType(EVENT).event(KF_CREATE_SESSION).handler(this.kfSessionHandler).end();
        // 自定义菜单事件
        messageRouter.rule().async(false).msgType(EVENT).event(EventType.CLICK).handler(this.menuHandler).end();
        // 关注事件
        messageRouter.rule().async(false).msgType(EVENT).event(EventType.SUBSCRIBE).handler(this.subscribeHandler).end();
        // 取消关注事件
        messageRouter.rule().async(false).msgType(EVENT).event(EventType.UNSUBSCRIBE).handler(this.unsubscribeHandler).end();
        // 扫描带参二维码事件
        messageRouter.rule().async(false).msgType(EVENT).event(EventType.SCAN).handler(this.scanHandler).end();
        // 其它事件
        messageRouter.rule().async(false).msgType(EVENT).handler(this.nullHandler).end();
        // 默认
        messageRouter.rule().async(false).handler(this.messageHandler).end();

        return messageRouter;
    }

}
