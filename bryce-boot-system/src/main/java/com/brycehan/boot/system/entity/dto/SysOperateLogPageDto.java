package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import com.brycehan.boot.common.enums.OperateStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统操作日志PageDto
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统操作日志PageDto")
public class SysOperateLogPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 操作名称
     */
    @Schema(description = "操作名称")
    @Length(max = 50)
    private String name;

    /**
     * 模块名
     */
    @Schema(description = "模块名")
    @Length(max = 50)
    private String moduleName;

    /**
     * 请求URI
     */
    @Schema(description = "请求URI")
    @Length(max = 2048)
    private String requestUri;

    /**
     * 操作状态（0：失败，1：成功）
     */
    @Schema(description = "操作状态（0：失败，1：成功）")
    private OperateStatus status;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    @Length(max = 50)
    private String username;

    /**
     * 机构ID
     */
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 操作时间开始
     */
    @Schema(description = "操作时间开始")
    private LocalDateTime operatedTimeStart;

    /**
     * 操作时间结束
     */
    @Schema(description = "操作时间结束")
    private LocalDateTime operatedTimeEnd;

}
