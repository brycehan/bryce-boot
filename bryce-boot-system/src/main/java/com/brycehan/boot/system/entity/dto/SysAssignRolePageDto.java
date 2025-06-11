package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.enums.YesNoType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统分配角色PageDto
 *
 * @since 2023/09/11
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统分配角色PageDto")
public class SysAssignRolePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 是否已分配
     */
    @Schema(description = "是否已分配")
    private YesNoType assigned;

}
