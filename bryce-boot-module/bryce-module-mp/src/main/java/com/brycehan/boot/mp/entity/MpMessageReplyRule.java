package com.brycehan.boot.mp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalTime;

/**
 * 微信公众号消息回复规则entity
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_mp_msg_reply_rule")
public class MpMessageReplyRule extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 匹配的关键词、事件等
     */
    private String matchValue;

    /**
     * 是否精确匹配（1：精确，1：不精确）
     */
    private Boolean exactMatch;

    /**
     * 回复消息类型
     */
    private String replyType;

    /**
     * 回复消息内容
     */
    private String replyContent;

    /**
     * 状态（0：停用，1：正常）
     */
    private Boolean status;

    /**
     * 生效起始时间
     */
    private LocalTime effectTimeStart;

    /**
     * 生效结束时间
     */
    private LocalTime effectTimeEnd;

    /**
     * 规则优先级
     */
    private Integer priority;

    /**
     * 备注
     */
    private String remark;

}
