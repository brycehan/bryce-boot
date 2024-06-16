package com.brycehan.boot.common.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录Vo
 *
 * @since 2022/5/10
 * @author Bryce Han
 */
@Data
@Schema(description = "登录Vo")
public class LoginVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    private String username;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String avatar;

    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌")
    private String accessToken;

    /**
     * 有效期
     */
    @Schema(description = "过期时间间隔，单位秒")
    private Long expiresIn;

}
