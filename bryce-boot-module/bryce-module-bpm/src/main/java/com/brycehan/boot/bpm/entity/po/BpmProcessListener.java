package com.brycehan.boot.bpm.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.entity.BaseEntity;

/**
 * 流程监听器entity
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_bpm_process_listener")
public class BpmProcessListener extends BaseEntity {

    /**
     * 监听器名称
     */
    private String name;

    /**
     * 状态
     */
    private StatusType status;

    /**
     * 监听类型
     */
    private String type;

    /**
     * 监听事件
     */
    private String event;

    /**
     * 值类型
     */
    private String valueType;

    /**
     * 值
     */
    private String value;

    /**
     * 租户编号
     */
    private Long tenantId;

}
