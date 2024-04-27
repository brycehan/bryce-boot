package com.brycehan.boot.api.system.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统用户Vo
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@Schema(description = "系统用户Vo")
public class SysUserApiVo implements Serializable {

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
     * 性别（0未知，1男，2女）
     */
    @Schema(description = "性别（0未知，1男，2女）")
    private String gender;

    /**
     * 所在国家
     */
    @Schema(description = "所在国家")
    private String country;

    /**
     * 所在省份
     */
    @Schema(description = "所在省份")
    private String province;

    /**
     * 所在城市
     */
    @Schema(description = "所在城市")
    private String city;

    /**
     * 用户语言
     */
    @Schema(description = "用户语言")
    private String language;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

}
