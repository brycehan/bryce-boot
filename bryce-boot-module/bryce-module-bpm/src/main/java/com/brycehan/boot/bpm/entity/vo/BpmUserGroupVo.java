package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.brycehan.boot.common.enums.StatusType;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;
import java.util.List;

/**
 * 用户组Vo
 *
 * @author Bryce Han
 * @since 2025/03/08
 */
@Data
@Schema(description = "用户组Vo")
public class BpmUserGroupVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Schema(description = "编号")
    private Long id;

    /**
     * 组名
     */
    @Schema(description = "组名")
    private String name;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     * 成员编号数组
     */
    @Schema(description = "成员编号数组")
    private List<String> userIds;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
