package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.brycehan.boot.common.enums.StatusType;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 流程监听器Vo
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Data
@Schema(description = "流程监听器Vo")
public class BpmProcessListenerVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID
     */
    @Schema(description = "主键 ID")
    private Long id;

    /**
     * 监听器名称
     */
    @Schema(description = "监听器名称")
    private String name;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private StatusType status;

    /**
     * 监听类型
     */
    @Schema(description = "监听类型")
    private String type;

    /**
     * 监听事件
     */
    @Schema(description = "监听事件")
    private String event;

    /**
     * 值类型
     */
    @Schema(description = "值类型")
    private String valueType;

    /**
     * 值
     */
    @Schema(description = "值")
    private String value;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    /**
     * 租户编号
     */
    @Schema(description = "租户编号")
    private Long tenantId;

}
