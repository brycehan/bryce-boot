package com.brycehan.boot.ma.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 微信小程序用户Vo
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@Schema(description = "微信小程序用户登录Vo")
public class MaUserLoginVo implements Serializable {

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
    private String account;

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
