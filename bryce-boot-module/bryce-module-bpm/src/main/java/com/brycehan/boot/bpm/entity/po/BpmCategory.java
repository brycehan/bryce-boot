package com.brycehan.boot.bpm.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.entity.BaseEntity;

/**
 * 流程分类entity
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_bpm_category")
public class BpmCategory extends BaseEntity {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类标志
     */
    private String code;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 租户编号
     */
    private Long tenantId;

}
