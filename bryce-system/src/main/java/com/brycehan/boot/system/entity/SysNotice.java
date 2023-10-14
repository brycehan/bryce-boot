package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.base.entity.BaseEntity;
import java.io.Serial;

/**
 * 系统通知公告entity
 *
 * @author Bryce Han
 * @since 2023/10/13
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("brc_sys_notice")
public class SysNotice extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 公告类型（0：通知，1：公告）
     */
    private Integer type;

    /**
     * 状态（0：关闭，1：正常）
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户ID
     */
    private Long tenantId;

}
