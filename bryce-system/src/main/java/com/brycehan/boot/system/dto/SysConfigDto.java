package com.brycehan.boot.system.dto;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.Serial;

/**
 * 系统配置表Dto
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Data
@TableName("brc_sys_config")
@Schema(description = "系统配置表Dto")
public class SysConfigDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 配置名称
     */
    @Schema(description = "配置名称")
    private String configName;

    /**
     * 配置键
     */
    @Schema(description = "配置键")
    private String configKey;

    /**
     * 配置值
     */
    @Schema(description = "配置值")
    private String configValue;

    /**
     * 是否系统内置（1：是，0：否）
     */
    @Schema(description = "是否系统内置（1：是，0：否）")
    private Boolean configType;

    /**
     * 创建人ID
     */
    @Schema(description = "创建人ID")
    private Long createdUserId;

    /**
     * 创建人账号
     */
    @Schema(description = "创建人账号")
    private String createUsername;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private Long updatedUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedTime;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
