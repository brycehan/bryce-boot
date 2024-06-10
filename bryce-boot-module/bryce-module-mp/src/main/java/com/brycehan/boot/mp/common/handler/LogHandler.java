package com.brycehan.boot.mp.common.handler;

import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.mp.entity.po.MpMessage;
import com.brycehan.boot.mp.service.MpMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 日志处理器
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LogHandler implements WxMpMessageHandler {

    private final MpMessageService mpMessageService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) {

        try {
            log.info("接收到请求消息，内容：{}", JsonUtils.writeValueAsString(wxMessage));
            this.mpMessageService.addMpMessage(MpMessage.buildMessage(wxMessage));
        } catch (Exception e) {
            log.error("记录消息异常：{}", e.getMessage());
        }

        return null;
    }
}
