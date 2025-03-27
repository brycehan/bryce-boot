package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.brycehan.boot.common.entity.BaseEntity;
import com.brycehan.boot.common.enums.StatusType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统部门 entity
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("brc_sys_dept")
public class SysDept extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 祖级部门列表
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
    private StatusType status;

}
