package com.brycehan.boot.pay.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 订单PageDto
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "订单PageDto")
public class PayOrderPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

}
