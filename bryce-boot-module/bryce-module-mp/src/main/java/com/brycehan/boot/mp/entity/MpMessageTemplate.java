package com.brycehan.boot.mp.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import me.chanjar.weixin.mp.bean.template.WxMpTemplate;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 微信公众号消息模板entity
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Data
@TableName("brc_mp_msg_template")
public class MpMessageTemplate implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 公众号模板ID
     */
    private String templateId;

    /**
     * 模版名称
     */
    private String name;

    /**
     * 标题
     */
    private String title;

    /**
     * 模板内容
     */
    private String content;

    /**
     * 消息内容
     */
    private Object data;

    /**
     * 链接
     */
    private String url;

    /**
     * 小程序信息
     */
    private Object miniProgram;

    /**
     * 是否有效
     */
    private Boolean status;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    /**
     * 创建微信公众号消息模板
     *
     * @param wxMpTemplate 微信公众号消息模板
     * @return 微信公众号消息模板
     */
    public static MpMessageTemplate create(WxMpTemplate wxMpTemplate) {
        MpMessageTemplate messageTemplate = new MpMessageTemplate();
        messageTemplate.setTemplateId(wxMpTemplate.getTemplateId());
        messageTemplate.setTitle(wxMpTemplate.getTitle());
        messageTemplate.setName(wxMpTemplate.getTemplateId());
        messageTemplate.setContent(wxMpTemplate.getContent());
        messageTemplate.setStatus(true);
        return messageTemplate;
    }

}
