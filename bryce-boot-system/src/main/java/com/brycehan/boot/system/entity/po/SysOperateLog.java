package com.brycehan.boot.system.entity.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 系统操作日志entity
 *
 * @since 2023/08/30
 * @author Bryce Han
 */
@Data
@TableName("brc_sys_operate_log")
public class SysOperateLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
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
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

}
