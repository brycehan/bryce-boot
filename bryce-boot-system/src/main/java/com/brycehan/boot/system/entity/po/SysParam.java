package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import com.brycehan.boot.common.enums.ParamType;
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
     * 参数类型（0：内置，0：应用）
     */
    private ParamType paramType;

    /**
     * 备注
     */
    private String remark;

}
