package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import com.brycehan.boot.common.enums.AccessType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统附件entity
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_attachment")
public class SysAttachment extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件路径
     */
    private String path;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 附件名后缀
     */
    private String suffix;

    /**
     * 访问类型
     */
    private AccessType accessType;

    /**
     * 附件大小（单位字节）
     */
    private Long size;

    /**
     * 哈希码
     */
    private String hash;

    /**
     * 存储平台
     */
    private String platform;

}
