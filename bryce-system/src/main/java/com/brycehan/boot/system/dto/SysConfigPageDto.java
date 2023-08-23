package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.validator.group.QueryGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统配置分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Getter
@Setter
@Schema(description = "SysConfigPageDto对象")
public class SysConfigPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 配置名称
     */
    @Size(max = 100, groups = QueryGroup.class)
    @Schema(description = "配置名称")
    private String configName;

    /**
     * 配置键
     */
    @Size(max = 100, groups = QueryGroup.class)
    @Schema(description = "配置键")
    private String configKey;

    /**
     * 配置值
     */
    @Size(max = 1000, groups = QueryGroup.class)
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
    private String createUserId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    @Schema(description = "修改人ID")
    private String updateUserId;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @Size(max = 500, groups = QueryGroup.class)
    @Schema(description = "备注")
    private String remark;

}
