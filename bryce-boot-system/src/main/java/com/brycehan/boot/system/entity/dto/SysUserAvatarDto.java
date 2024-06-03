package com.brycehan.boot.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 个人信息Dto
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Schema(description = "个人信息Dto")
public class SysUserAvatarDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    @NotEmpty
    @Size(max = 200)
    private String avatar;

}
