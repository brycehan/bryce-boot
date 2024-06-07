package com.brycehan.boot.common.base.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 短信验证码Vo
 *
 * @author Bryce Han
 * @since 2023/10/4
 */
@Data
@Schema(description = "短信验证码Vo")
public class SmsCodeVo implements Serializable {

    /**
     * key
     */
    @Schema(description = "key")
    private String key;

}
