package com.brycehan.boot.mp.common.handler;

import com.brycehan.boot.mp.service.MpMessageReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用户扫码的处理器
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScanHandler implements WxMpMessageHandler {

    private final MpMessageReplyService mpMessageReplyService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        // 扫码事件处理
        log.info("用户扫描带参二维码，openid：{}", wxMessage.getFromUser());
        this.mpMessageReplyService.tryAutoReply(true, wxMessage.getFromUser(), wxMessage.getEventKey());

        return null;
    }
}
