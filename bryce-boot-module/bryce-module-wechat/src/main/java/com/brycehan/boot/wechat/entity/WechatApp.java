package com.brycehan.boot.wechat.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.base.entity.BaseEntity;
import java.io.Serial;

/**
 * 微信应用entity
 *
 * @author Bryce Han
 * @since 2023/11/06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_wechat_app")
public class WechatApp extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用密钥
     */
    private String appSecret;

    /**
     * 类型（mp：微信公众号，ma：微信小程序）
     */
    private String type;

    /**
     * 令牌
     */
    private String token;

    /**
     * 重定向地址
     */
    private String redirectUrl;

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 状态（0：停用，1：正常）
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

}
