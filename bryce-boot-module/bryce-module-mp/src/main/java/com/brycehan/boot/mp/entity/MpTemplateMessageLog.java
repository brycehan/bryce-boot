package com.brycehan.boot.mp.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.util.JsonUtils;
import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信公众号模版消息发送记录entity
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@TableName("brc_mp_template_msg_log")
public class MpTemplateMessageLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 用户openid
     */
    private String toUser;

    /**
     * templateid
     */
    private String templateId;

    /**
     * 消息数据
     */
    private String data;

    /**
     * 消息链接
     */
    private String url;

    /**
     * 小程序信息
     */
    private String miniProgram;

    /**
     * 发送时间
     */
    private LocalDateTime sendTime;

    /**
     * 发送结果
     */
    private String sendResult;

    /**
     * 创建模板消息日志
     *
     * @param message 消息
     * @param sendResult 发送结果
     * @return 模板消息日志
     */
    public static MpTemplateMessageLog create(WxMpTemplateMessage message, String sendResult) {
        MpTemplateMessageLog templateMessageLog = new MpTemplateMessageLog();
        templateMessageLog.setTemplateId(message.getTemplateId());
        templateMessageLog.setToUser(message.getToUser());
        templateMessageLog.setUrl(message.getUrl());
        templateMessageLog.setMiniProgram(JsonUtils.writeValueAsString(message.getMiniProgram()));
        templateMessageLog.setData(JsonUtils.writeValueAsString(message.getData()));
        templateMessageLog.setSendTime(LocalDateTime.now());
        templateMessageLog.setSendResult(sendResult);

        return templateMessageLog;
    }
}
