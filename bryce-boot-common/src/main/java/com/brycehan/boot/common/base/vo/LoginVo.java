package com.brycehan.boot.common.base.vo;

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
     * ID
     */
    @Schema(description = "ID")
    private Long id;

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
     * 账户名
     */
    @Schema(description = "账户名")
    private String username;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 令牌
     */
    @Schema(description = "令牌")
    private String token;

}
