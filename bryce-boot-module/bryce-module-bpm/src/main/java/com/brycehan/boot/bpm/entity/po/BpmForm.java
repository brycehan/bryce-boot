package com.brycehan.boot.bpm.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import com.brycehan.boot.common.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表单定义entity
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_bpm_form")
public class BpmForm extends BaseEntity {

    /**
     * 表单名
     */
    private String name;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 表单的配置
     */
    private String conf;

    /**
     * 表单项的数组
     */
    private String fields;

    /**
     * 备注
     */
    private String remark;

    /**
     * 租户编号
     */
    private Long tenantId;

}
