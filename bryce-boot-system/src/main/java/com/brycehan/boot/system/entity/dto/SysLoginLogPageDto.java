package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 系统登录日志PageDto
 *
 * @since 2023/09/26
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统登录日志PageDto")
public class SysLoginLogPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    @Schema(description = "用户账号")
    @Size(max = 50)
    private String username;

    /**
     * 登录IP地址
     */
    @Schema(description = "登录IP地址")
    @Size(max = 128)
    private String ip;

    /**
     * 状态（0：失败，1：成功）
     */
    @Schema(description = "状态（0：失败，1：成功）")
    private Boolean status;

    /**
     * 访问时间开始
     */
    @Schema(description = "访问时间开始")
    private LocalDateTime accessTimeStart;

    /**
     * 访问时间结束
     */
    @Schema(description = "访问时间结束")
    private LocalDateTime accessTimeEnd;

}
