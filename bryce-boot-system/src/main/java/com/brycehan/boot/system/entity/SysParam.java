package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.base.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统参数entity
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_param")
public class SysParam extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数名称
     */
    private String paramName;

    /**
     * 参数键名
     */
    private String paramKey;

    /**
     * 参数值
     */
    private String paramValue;

    /**
     * 参数类型（built_in：内置，system：系统）
     */
    private String paramType;

    /**
     * 备注
     */
    private String remark;

}
