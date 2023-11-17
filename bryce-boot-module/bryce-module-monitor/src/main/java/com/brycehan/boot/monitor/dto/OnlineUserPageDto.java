package com.brycehan.boot.monitor.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 在线用户 PageDto
 *
 * @since 2023/09/11
 * @author Bryce Han
 */
@Schema(description = "在线用户 PageDto")
@Data
@EqualsAndHashCode(callSuper = false)
public class OnlineUserPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 账号
     */
    @Schema(description = "账号")
    @Size(max = 50)
    private String username;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    @Size(max = 20)
    private String phone;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
