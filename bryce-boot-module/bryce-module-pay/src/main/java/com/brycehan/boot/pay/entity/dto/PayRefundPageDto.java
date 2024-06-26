package com.brycehan.boot.pay.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 退款单PageDto
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@Schema(description = "退款单PageDto")
@EqualsAndHashCode(callSuper = true)
public class PayRefundPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

}
