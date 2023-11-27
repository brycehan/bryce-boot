package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDate;

/**
 * 系统租户entity
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_tenant")
public class SysTenant extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 站点域名
     */
    private String siteDomain;

    /**
     * 访问网址
     */
    private String siteUrl;

    /**
     * logo
     */
    private String siteLogo;

    /**
     * 系统配置
     */
    private String config;

    /**
     * 过期时间
     */
    private LocalDate expiresTime;

    /**
     * 状态（0：停用，1：正常）
     */
    private Boolean status;

}
