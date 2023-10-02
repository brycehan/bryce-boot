package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.base.entity.BaseEntity;
import java.io.Serial;

/**
 * 系统附件entity
 *
 * @author Bryce Han
 * @since 2023/10/01
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("brc_sys_attachment")
public class SysAttachment extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 附件大小（单位字节）
     */
    private Long size;

    /**
     * 附件类型
     */
    private String type;

    /**
     * 附件名后缀
     */
    private String suffix;

    /**
     * 哈希码
     */
    private String hash;

    /**
     * 存储平台
     */
    private String platform;

    /**
     * 租户ID
     */
    private Long tenantId;

}
