package com.brycehan.boot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统角色Dto
 *
 * @since 2023/09/13
 * @author Bryce Han
 */
@Data
@Schema(description = "系统角色Dto")
public class SysRoleDataScopeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull(message = "角色ID不能为空")
    private Long id;

    /**
     * 数据范围（1：全部数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据，5：自定义数据）
     */
    @Schema(description = "数据范围（1：全部数据，2：本机构及以下机构数据，3：本机构数据，4：本人数据，5：自定义数据）")
    @NotNull(message = "数据范围不能为空")
    private Integer dataScope;

    /**
     * 机构IDs
     */
    @Schema(description = "机构IDs")
    private List<Long> orgIds;

}
