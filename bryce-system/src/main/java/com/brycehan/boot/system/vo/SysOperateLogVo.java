package com.brycehan.boot.system.vo;

import com.brycehan.boot.system.entity.SysOrg;
import com.fhs.core.trans.anno.Trans;
import com.fhs.core.trans.constant.TransType;
import com.fhs.core.trans.vo.TransPojo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

import java.io.Serializable;
import java.io.Serial;

/**
 * 系统操作日志Vo
 *
 * @author Bryce Han
 * @since 2023/09/27
 */
@Data
@Schema(description = "系统操作日志Vo")
public class SysOperateLogVo implements Serializable, TransPojo {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 操作名称
     */
    @Schema(description = "操作名称")
    private String name;

    /**
     * 模块名
     */
    @Schema(description = "模块名")
    private String moduleName;

    /**
     * 请求URI
     */
    @Schema(description = "请求URI")
    private String requestUri;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private String requestMethod;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String requestParam;

    /**
     * 返回消息
     */
    @Schema(description = "返回消息")
    private String resultMessage;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    @Trans(type = TransType.DICTIONARY, key = "sys_operate_type", ref = "operatedTypeName")
    private String operatedType;

    /**
     * 操作类型名称
     */
    @Schema(description = "操作类型名称")
    private String operatedTypeName;

    /**
     * 操作时间
     */
    @Schema(description = "操作时间")
    private LocalDateTime operatedTime;

    /**
     * 执行时长（毫秒）
     */
    @Schema(description = "执行时长（毫秒）")
    private Integer duration;

    /**
     * 操作状态（0：失败，1：成功）
     */
    @Schema(description = "操作状态（0：失败，1：成功）")
    private Boolean status;

    /**
     * User Agent
     */
    @Schema(description = "User Agent")
    private String userAgent;

    /**
     * 操作IP
     */
    @Schema(description = "操作IP")
    private String ip;

    /**
     * 操作地点
     */
    @Schema(description = "操作地点")
    private String location;

    /**
     * 操作人ID
     */
    @Schema(description = "操作人ID")
    private Long userId;

    /**
     * 操作人账号
     */
    @Schema(description = "操作人账号")
    private String username;

    /**
     * 机构ID
     */
    @Trans(type = TransType.SIMPLE, target = SysOrg.class, fields = "name", ref = "orgName")
    @Schema(description = "机构ID")
    private Long orgId;

    /**
     * 机构名称
     */
    @Schema(description = "机构名称")
    private String orgName;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

}
