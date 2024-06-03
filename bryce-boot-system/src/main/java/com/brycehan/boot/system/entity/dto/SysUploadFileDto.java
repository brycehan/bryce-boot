package com.brycehan.boot.system.entity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 系统上传文件Dto
 *
 * @since 2023/10/01
 * @author Bryce Han
 */
@Data
@Schema(description = "系统上传文件Dto")
public class SysUploadFileDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 文件
     */
    @Schema(description = "文件")
    @Size(min = 1, max = 100, message = "文件有效个数在1-100")
    private List<MultipartFile> file;

}
