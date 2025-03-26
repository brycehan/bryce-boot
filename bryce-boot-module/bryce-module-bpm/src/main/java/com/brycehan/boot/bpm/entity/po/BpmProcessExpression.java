package com.brycehan.boot.bpm.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.entity.BaseEntity;

/**
 * 流程表达式 entity
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_bpm_process_expression")
public class BpmProcessExpression extends BaseEntity {

    /**
     * 流程实例的名称
     */
    private String name;

    /**
     * 流程实例的状态
     */
    private StatusType status;

    /**
     * 表达式
     */
    private String expression;

    /**
     * 租户编号
     */
    private Long tenantId;

}
