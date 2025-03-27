package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import com.brycehan.boot.common.enums.StatusType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统角色PageDto
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统角色PageDto")
public class SysRolePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @Length(max = 50)
    private String name;

    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @Length(max = 50)
    private String code;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private StatusType status;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private Long deptId;

    /**
     * 创建时间开始
     */
    @Schema(description = "创建时间开始")
    private LocalDateTime createdTimeStart;

    /**
     * 创建时间结束
     */
    @Schema(description = "创建时间结束")
    private LocalDateTime createdTimeEnd;

}
