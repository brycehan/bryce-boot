package com.brycehan.boot.api.system.dto;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程部门 Dto
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Data
public class BpmDeptDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 部门编号
     */
    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 父部门编号
     */
    private Long parentId;
    /**
     * 负责人的用户编号
     */
    private Long leaderUserId;
    /**
     * 部门状态
     * <br/>
     * 枚举 {@link com.brycehan.boot.common.enums.StatusType}
     */
    private Integer status;

}
