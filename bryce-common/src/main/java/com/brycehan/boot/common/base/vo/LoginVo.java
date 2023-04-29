package com.brycehan.boot.common.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

/**
 * jwt视图对象
 *
 * @author Bryce Han
 * @since 2022/5/10
 */
@Schema(description = "Jwt令牌Vo")
@Builder
@Data
public class LoginVo {

    /**
     * jwt令牌
     */
    @Schema(description = "jwt令牌")
    private String token;

    /**
     * 登录类型
     */
    // TODO 枚举类型校验 account/mobile
    @Schema(description = "登录类型")
    private String type;

}
