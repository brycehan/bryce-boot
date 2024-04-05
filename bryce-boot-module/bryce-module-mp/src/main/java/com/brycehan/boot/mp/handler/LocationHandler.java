package com.brycehan.boot.mp.handler;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 位置处理器
 *
 * @author Bryce Han
 * @since 2024/3/25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocationHandler implements WxMpMessageHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context,
                                    WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        if (WxConsts.XmlMsgType.LOCATION.equals(wxMessage.getMsgType())) {

            log.info("上报地理位置，纬度：{}，经度：{}，精度：{}",
                    wxMessage.getLatitude(), wxMessage.getLongitude(), wxMessage.getPrecision());

            String content = "感谢反馈，您的地理位置已收到！";
            return new TextBuilder().content(content).build();
        }

        return null;
    }

}
