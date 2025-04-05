package com.brycehan.boot.bpm.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;

/**
 * 流程模型标识 Dto
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "流程模型标识 Dto")
public class BpmModelKeyDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 流程标识
     */
    @Schema(description = "流程标识")
    @Length(max = 30)
    @Pattern(regexp = "^[a-zA-Z_][\\-.a-zA-Z0-9_$]*$")
    private String key;

}
