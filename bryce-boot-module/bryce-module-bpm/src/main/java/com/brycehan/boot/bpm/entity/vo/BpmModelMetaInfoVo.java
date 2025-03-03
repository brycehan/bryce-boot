package com.brycehan.boot.bpm.entity.vo;

import com.brycehan.boot.common.enums.VisibleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * BPM 流程 MetaInfo Dto
 * <br>
 * <br>
 * 主要用于 { Model#setMetaInfo(String)} 的存储
 * <br>
 * 它的字段和
 * {@link com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo}
 * 是一致的
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Data
@Schema(description = "流程模型元信息 Vo")
public class BpmModelMetaInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 流程图标
     */
    @Schema(description = "流程图标", example = "https://www.brycehan.com/bryce.jpg")
    private String icon;

    /**
     * 流程描述
     */
    @Schema(description = "流程描述", example = "我是描述")
    private String description;

    /**
     * 流程类型
     */
    @Schema(description = "流程类型")
    private Integer type;

    /**
     * 表单类型
     */
    @Schema(description = "表单类型")
    private Integer formType;

    /**
     * 表单编号
     */
    @Schema(description = "表单编号")
    private Long formId; // formType 为 NORMAL 使用，必须非空

    /**
     * 自定义表单的提交路径，使用 Vue 的路由地址
     */
    @Schema(description = "自定义表单的提交路径", example = "/bpm/oa/leave/create")
    private String formCustomCreatePath; // 表单类型为 CUSTOM 时，必须非空

    /**
     * 自定义表单的查看路径，使用 Vue 的路由地址
     */
    @Schema(description = "自定义表单的查看路径", example = "/bpm/oa/leave/view")
    private String formCustomViewPath; // 表单类型为 CUSTOM 时，必须非空

    /**
     * 是否可见
     */
    @Schema(description = "是否可见")
    private VisibleType visible;

    /**
     * 可发起用户编号数组
     */
    @Schema(description = "可发起用户编号数组")
    private List<Long> startUserIds;

    /**
     * 可管理用户编号数组
     */
    @Schema(description = "可管理用户编号数组")
    private List<Long> managerUserIds;

    /**
     * 排序
     */
    @Schema(description = "排序", example = "1")
    private Long sort; // 创建时，后端自动生成

    /**
     * 允许取消正在
     */
    @Schema(description = "允许撤销审批中的申请", example = "true")
    private Boolean allowCancelRunningProcess;

    /**
     * 流程 ID 规则
     */
    @Schema(description = "流程 ID 规则", example = "{}")
    private ProcessIdRule processIdRule;

    /**
     * 自动去重类型
     */
    @Schema(description = "自动去重类型", example = "1")
    private Integer autoApprovalType;

    /**
     * 标题设置
     */
    @Schema(description = "标题设置", example = "{}")
    private TitleSetting titleSetting;

    /**
     * 摘要设置
     */
    @Schema(description = "摘要设置", example = "{}")
    private SummarySetting summarySetting;

    /**
     * 流程 ID 规则
     */
    @Data
    @Schema(description = "流程 ID 规则")
    public static class ProcessIdRule {

        @Schema(description = "是否启用", example = "false")
        private Boolean enable;

        @Schema(description = "前缀", example = "XX")
        private String prefix;

        @Schema(description = "中缀", example = "20250120")
        private String infix; // 精确到日、精确到时、精确到分、精确到秒

        @Schema(description = "后缀", example = "YY")
        private String postfix;

        @Schema(description = "序列长度", example = "5")
        private Integer length;
    }

    /**
     * 标题设置
     */
    @Data
    @Schema(description = "标题设置")
    public static class TitleSetting {

        @Schema(description = "是否自定义", example = "false")
        private Boolean enable;

        @Schema(description = "标题", example = "流程标题")
        private String title;
    }

    /**
     * 摘要设置
     */
    @Data
    @Schema(description = "摘要设置")
    public static class SummarySetting {

        @Schema(description = "是否自定义", example = "false")
        private Boolean enable;

        @Schema(description = "摘要字段数组", example = "[]")
        private List<String> summary;
    }
}
