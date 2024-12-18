package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.DataScopeType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统角色机构Dto
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统角色机构Dto")
public class SysRoleOrgDto extends BaseDto {

    /**
     * ID
     */
    @NotNull
    @Schema(description = "角色ID")
    private Long id;

    /**
     * 数据范围
     */
    @Schema(description = "数据范围")
    @NotNull
    private DataScopeType dataScope;

    /**
     * 机构IDs
     */
    @Schema(description = "机构IDs")
    private List<Long> orgIds;

}
