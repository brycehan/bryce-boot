package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.base.entity.BaseEntity;
import java.io.Serial;

/**
 * 系统机构entity
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("brc_sys_org")
public class SysOrg extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 机构编码
     */
    private String code;

    /**
     * 父机构ID
     */
    private Long parentId;

    /**
     * 祖级机构列表
     */
    private String ancestor;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String contactNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    private Boolean status;

    /**
     * 租户ID
     */
    private Long tenantId;

}
