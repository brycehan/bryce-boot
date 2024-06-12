package com.brycehan.boot.system.entity.dto;

import com.brycehan.boot.common.base.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统上传文件Dto
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统上传文件Dto")
public class SysUploadFileDto extends BaseDto {

    /**
     * 文件
     */
    @Schema(description = "文件")
    @Size(min = 1, max = 100, message = "文件有效个数在1-100")
    private List<MultipartFile> file;

}
