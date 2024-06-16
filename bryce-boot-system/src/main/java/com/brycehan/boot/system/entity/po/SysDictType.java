package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统字典类型entity
 *
 * @since 2023/09/05
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_dict_type")
public class SysDictType extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典类型
     */
    private String dictType;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 状态（false：停用，true：正常）
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

}
