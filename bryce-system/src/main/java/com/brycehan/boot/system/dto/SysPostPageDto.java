package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.validator.group.QueryGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统岗位分页数据传输对象
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "SysPostPageDto对象")
public class SysPostPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 岗位编码
     */
    @Size(max = 64, groups = QueryGroup.class)
    @Schema(description = "岗位编码")
    private String postCode;

    /**
     * 岗位名称
     */
    @Size(max = 50, groups = QueryGroup.class)
    @Schema(description = "岗位名称")
    private String postName;

    /**
     * 显示顺序
     */
    @Schema(description = "显示顺序")
    private Integer sortNumber;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Integer status;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}
