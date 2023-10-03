package com.brycehan.boot.system.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

import java.io.Serial;

/**
 * 系统操作日志PageDto
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Data
@Schema(description = "系统操作日志PageDto")
@EqualsAndHashCode(callSuper = false)
public class SysOperateLogPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作名称
     */
    @Schema(description = "操作名称")
    @Size(max = 50)
    private String name;

    /**
     * 模块名
     */
    @Schema(description = "模块名")
    @Size(max = 50)
    private String moduleName;

    /**
     * 请求URI
     */
    @Schema(description = "请求URI")
    @Size(max = 2048)
    private String requestUri;

    /**
     * 操作状态（0：失败，1：成功）
     */
    @Schema(description = "操作状态（0：失败，1：成功）")
    private Boolean status;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    @Size(max = 50)
    private String username;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

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
