package com.brycehan.boot.mp.common.handler;

import com.brycehan.boot.mp.entity.po.MpMessage;
import com.brycehan.boot.mp.service.MpMessageReplyService;
import com.brycehan.boot.mp.service.MpMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Bryce Han
 * @since 2024/3/25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageHandler implements WxMpMessageHandler {

    private final MpMessageReplyService mpMessageReplyService;
    private final MpMessageService mpMessageService;

    private static final String TRANSFER_CUSTOMER_SERVICE_KEY = "人工";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        String content = wxMessage.getContent();
        String fromUser = wxMessage.getFromUser();
        boolean autoReplied = this.mpMessageReplyService.tryAutoReply(false, fromUser, content);

        // 当用户输入关键词如“您好”、“客服”等，并且有客服在线时，把消息转发给在线客服
        if (TRANSFER_CUSTOMER_SERVICE_KEY.equals(content) || !autoReplied) {
            this.mpMessageService.addMpMessage(MpMessage.buildOutMessage(WxConsts.KefuMsgType.TRANSFER_CUSTOMER_SERVICE, fromUser, null));
            return WxMpXmlOutMessage
                    .TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(fromUser)
                    .build();
        }

        return null;
    }
}
