package com.brycehan.boot.pay.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.io.Serial;

/**
 * 支付记录 PageDto
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Data
@Schema(description = "支付记录 PageDto")
@EqualsAndHashCode(callSuper = true)
public class PayPaymentPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

}
