package com.brycehan.boot.mp.common.handler;

import com.brycehan.boot.mp.service.MpMessageReplyService;
import com.brycehan.boot.mp.service.MpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户关注处理器
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SubscribeHandler implements WxMpMessageHandler {

    private final MpUserService mpUserService;
    private final MpMessageReplyService mpMessageReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) {

        log.info("新关注用户 openid：{}，事件：{}", wxMessage.getFromUser(), wxMessage.getEvent());
        this.mpUserService.refreshUserInfo(wxMessage.getFromUser());

        this.mpMessageReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEvent());

        // 处理特殊事件，如用户扫描带参二维码关注
        if (StringUtils.isNoneBlank(wxMessage.getEventKey())) {
            this.mpMessageReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEventKey());
        }

        return null;
    }
}
