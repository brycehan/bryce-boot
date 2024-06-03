package com.brycehan.boot.ma.entity.dto;

import com.brycehan.boot.common.base.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 微信小程序用户 PageDto
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Data
@Schema(description = "微信小程序用户 PageDto")
@EqualsAndHashCode(callSuper = true)
public class MaUserPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

}
