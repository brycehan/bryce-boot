package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BaseDto;
import com.brycehan.boot.common.enums.DataScope;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 系统角色Dto
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统角色Dto")
public class SysRoleDataScopeDto extends BaseDto {

    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull(message = "角色ID不能为空")
    private Long id;

    /**
     * 数据范围（0：全部数据，1：本机构及以下机构数据，2：本机构数据，3：本人数据，4：自定义数据）
     */
    @Schema(description = "数据范围（0：全部数据，1：本机构及以下机构数据，2：本机构数据，3：本人数据，4：自定义数据）")
    @NotNull(message = "数据范围不能为空")
    private DataScope dataScope;

    /**
     * 机构IDs
     */
    @Schema(description = "机构IDs")
    private List<Long> orgIds;

}
