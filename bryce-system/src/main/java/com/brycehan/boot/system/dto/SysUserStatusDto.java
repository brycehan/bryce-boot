package com.brycehan.boot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统用户状态数据传输对象
 *
 * @author Bryce Han
 * @since 2022/5/14
 */
@Getter
@Setter
@Schema(description = "SysUserStatusDto")
public class SysUserStatusDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 状态（0：停用，1：正常）
     */
    @Schema(description = "状态（0：停用，1：正常）")
    private Integer status;

}
