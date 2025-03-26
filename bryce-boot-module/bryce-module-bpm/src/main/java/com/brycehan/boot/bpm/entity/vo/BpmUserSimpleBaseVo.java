package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户精简信息 Vo
 *
 * @since 2025/3/11
 * @author Bryce Han
 */
@Schema(description = "用户精简信息 Vo")
@Data
public class BpmUserSimpleBaseVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户编号")
    private Long id;
    @Schema(description = "用户昵称")
    private String nickname;
    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "部门编号")
    private Long deptId;
    @Schema(description = "部门名称")
    private String deptName;

}