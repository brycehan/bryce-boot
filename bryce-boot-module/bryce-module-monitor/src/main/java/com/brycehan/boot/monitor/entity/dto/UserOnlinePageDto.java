package com.brycehan.boot.monitor.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;

/**
 * 在线用户PageDto
 *
 * @since 2023/09/11
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "在线用户PageDto")
public class UserOnlinePageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Length(max = 50)
    private String username;

    /**
     * 登录IP
     */
    @Schema(description = "登录IP")
    @Length(max = 50)
    private String loginIp;

}
