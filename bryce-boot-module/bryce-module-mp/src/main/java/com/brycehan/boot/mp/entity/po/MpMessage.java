package com.brycehan.boot.mp.entity.po;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.mp.common.MpMessageType;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信公众号消息entity
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@TableName("brc_mp_msg")
public class MpMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * openid
     */
    private String openId;

    /**
     * 消息方向（1：in，0：out）
     */
    private Boolean inOut;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    public static MpMessage buildMessage(WxMpXmlMessage wxMpXmlMessage) {
        MpMessage mpMessage = new MpMessage();
        mpMessage.setOpenId(wxMpXmlMessage.getOpenId());
        mpMessage.setInOut(MpMessageType.IN.isValue());
        mpMessage.setMessageType(wxMpXmlMessage.getMsgType());

        JSONObject jsonObject = new JSONObject();

        switch (mpMessage.getMessageType()) {
            case WxConsts.XmlMsgType.TEXT -> jsonObject.put("content", wxMpXmlMessage.getContent());
            case WxConsts.XmlMsgType.IMAGE -> {
                jsonObject.put("picUrl", wxMpXmlMessage.getPicUrl());
                jsonObject.put("mediaId", wxMpXmlMessage.getMediaId());
            }
            case WxConsts.XmlMsgType.VOICE -> {
                jsonObject.put("format", wxMpXmlMessage.getFormat());
                jsonObject.put("mediaId", wxMpXmlMessage.getMediaId());
            }
            case WxConsts.XmlMsgType.VIDEO, WxConsts.XmlMsgType.SHORTVIDEO -> {
                jsonObject.put("thumbMediaId", wxMpXmlMessage.getThumbMediaId());
                jsonObject.put("mediaId", wxMpXmlMessage.getMediaId());
            }
            case WxConsts.XmlMsgType.LOCATION -> {
                jsonObject.put("locationX", wxMpXmlMessage.getLocationX());
                jsonObject.put("locationY", wxMpXmlMessage.getLocationY());
                jsonObject.put("scale", wxMpXmlMessage.getScale());
                jsonObject.put("label", wxMpXmlMessage.getLabel());
            }
            case WxConsts.XmlMsgType.LINK -> {
                jsonObject.put("title", wxMpXmlMessage.getTitle());
                jsonObject.put("description", wxMpXmlMessage.getDescription());
                jsonObject.put("url", wxMpXmlMessage.getUrl());
            }
            case WxConsts.XmlMsgType.EVENT -> {
                jsonObject.put("event", wxMpXmlMessage.getEvent());
                jsonObject.put("eventKey", wxMpXmlMessage.getEventKey());
            }
            default -> {}
        }

        mpMessage.setContent(jsonObject.toJSONString());

        return mpMessage;
    }

    /**
     * 构建回复消息
     * @param messageType 消息类型
     * @param openid openid
     * @param jsonObject 消息内容
     * @return 回复消息
     */
    public static MpMessage buildOutMessage(String messageType, String openid, JSONObject jsonObject) {
        MpMessage mpMessage = new MpMessage();
        mpMessage.setMessageType(messageType);
        mpMessage.setOpenId(openid);
        mpMessage.setContent(JSONObject.toJSONString(jsonObject));
        mpMessage.setInOut(MpMessageType.OUT.isValue());

        return mpMessage;
    }

}
