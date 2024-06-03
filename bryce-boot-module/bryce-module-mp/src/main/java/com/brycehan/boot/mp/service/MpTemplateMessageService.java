package com.brycehan.boot.mp.service;

import com.brycehan.boot.mp.entity.dto.MpTemplateMessageBatchDto;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

/**
 * 微信公众号模板消息服务
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
public interface MpTemplateMessageService {

    /**
     * 发送微信模板消息
     *
     * @param message 消息
     */
    void sendTemplateMessage(WxMpTemplateMessage message);

    /**
     * 批量发送消息
     *
     * @param templateMessageBatchDto 消息
     */
    void sendMessageBatch(MpTemplateMessageBatchDto templateMessageBatchDto);

}
