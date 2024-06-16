package com.brycehan.boot.framework.operatelog;

import com.brycehan.boot.common.entity.BaseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 系统操作日志Dto
 *
 * @since 2022/11/18
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统操作日志Dto")
public class OperateLogDto extends BaseDto {

    /**
     * ID
     */
    private Long id;

    /**
     * 操作名称
     */
    private String name;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求参数
     */
    private String requestParam;

    /**
     * 返回消息
     */
    private String resultMessage;

    /**
     * 操作类型
     */
    private String operatedType;

    /**
     * 操作时间
     */
    private LocalDateTime operatedTime;

    /**
     * 执行时长（毫秒）
     */
    private Integer duration;

    /**
     * 操作状态（0：失败，1：成功）
     */
    private Boolean status;

    /**
     * User Agent
     */
    private String userAgent;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作地点
     */
    private String location;

    /**
     * 操作人ID
     */
    private Long userId;

    /**
     * 操作人账号
     */
    private String username;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

}
