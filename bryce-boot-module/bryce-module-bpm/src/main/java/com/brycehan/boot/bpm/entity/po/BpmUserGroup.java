package com.brycehan.boot.bpm.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.entity.BaseEntity;

import java.util.List;

/**
 * 用户组entity
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "brc_bpm_user_group", autoResultMap = true)
public class BpmUserGroup extends BaseEntity {

    /**
     * 组名
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 成员编号数组
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<Long> userIds;

    /**
     * 状态（0：停用，1：正常）
     */
    private StatusType status;

    /**
     * 租户编号
     */
    private Long tenantId;

}
