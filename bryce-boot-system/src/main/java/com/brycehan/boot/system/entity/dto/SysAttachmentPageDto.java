package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.entity.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 系统附件PageDto
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统附件PageDto")
public class SysAttachmentPageDto extends BasePageDto {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 附件名称
     */
    @Schema(description = "附件名称")
    @Size(max = 100)
    private String name;

    /**
     * 附件类型
     */
    @Schema(description = "附件类型")
    @Size(max = 50)
    private String type;

    /**
     * 存储平台
     */
    @Schema(description = "存储平台")
    @Size(max = 50)
    private String platform;

}
