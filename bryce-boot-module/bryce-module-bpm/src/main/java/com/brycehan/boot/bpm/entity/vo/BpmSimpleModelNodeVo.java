package com.brycehan.boot.bpm.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 仿钉钉流程设计模型节点 Vo
 *
 * @author Bryce Han
 * @since 2025/3/2
 */
@Data
@Schema(description = "仿钉钉流程设计模型节点 Vo")
public class BpmSimpleModelNodeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 模型节点编号
     */
    @NotEmpty
    @Schema(description = "模型节点编号")
    private String id;

    /**
     * 模型节点类型
     */
    @NotNull
    @Schema(description = "模型节点类型")
    private Integer type;

    /**
     * 模型节点名称
     */
    @Schema(description = "模型节点名称")
    private String name;

    /**
     * 节点展示内容
     */
    @Schema(description = "节点展示内容")
    private String showText;

    /**
     * 子节点
     */
    @Schema(description = "子节点")
    private BpmSimpleModelNodeVo childNode; // 补充说明：在该模型下，子节点有且仅有一个，不会有多个

    /**
     * 候选人
     */
    @Schema(description = "候选人策略")
    private Integer candidateStrategy; // 用于审批，抄送节点

    /**
     * 候选人参数
     */
    @Schema(description = "候选人参数")
    private String candidateParam; // 用于审批，抄送节点

    /**
     * 审批节点类型
     */
    @Schema(description = "审批节点类型")
    private Integer approveType; // 用于审批节点

    /**
     * 多人审批方式
     */
    @Schema(description = "多人审批方式")
    private Integer approveMethod; // 用于审批节点

    /**
     * 通过比例
     */
    @Schema(description = "通过比例", example = "100")
    private Integer approveRatio; // 通过比例，当多人审批方式为：多人会签(按通过比例) 需要设置

    /**
     * 表单权限
     */
    @Schema(description = "表单权限", example = "[]")
    private List<Map<String, String>> fieldsPermission;

    /**
     * 操作按钮设置
     */
    @Schema(description = "操作按钮设置", example = "[]")
    private List<OperationButtonSetting> buttonsSetting;  // 用于审批节点

    /**
     * 是否需要签名
     */
    @Schema(description = "是否需要签名", example = "false")
    private Boolean signEnable;

    /**
     * 是否填写审批意见
     */
    @Schema(description = "是否填写审批意见", example = "false")
    private Boolean reasonRequire;

    /**
     * 审批节点拒绝处理
     */
    @Schema(description = "审批节点拒绝处理")
    private RejectHandler rejectHandler;

    /**
     * 审批节点超时处理
     */
    @Schema(description = "审批节点超时处理")
    private TimeoutHandler timeoutHandler;

    /**
     * 审批节点的审批人与发起人相同时，对应的处理类型
     */
    @Schema(description = "审批节点的审批人与发起人相同时，对应的处理类型", example = "1")
    private Integer assignStartUserHandlerType;

    /**
     * 空处理策略
     */
    private AssignEmptyHandler assignEmptyHandler;

    /**
     * 创建任务监听器
     */
    private ListenerHandler taskCreateListener;

    /**
     * 指派任务监听器
     */
    private ListenerHandler taskAssignListener;

    /**
     * 完成任务监听器
     */
    private ListenerHandler taskCompleteListener;

    /**
     * 延迟器设置
     */
    @Schema(description = "延迟器设置", example = "{}")
    private DelaySetting delaySetting;

    /**
     * 条件节点
     */
    @Schema(description = "条件节点")
    private List<BpmSimpleModelNodeVo> conditionNodes; // 补充说明：有且仅有条件、并行、包容分支会使用

    /**
     * 条件节点设置
     */
    private ConditionSetting conditionSetting; // 仅用于条件节点 BpmSimpleModelNodeType.CONDITION_NODE

    /**
     * 路由分支组
     */
    @Schema(description = "路由分支组", example = "[]")
    private List<RouterSetting> routerGroups;

    /**
     * 路由分支默认分支 ID
     */
    @Schema(description = "路由分支默认分支 ID") // 由后端生成
    private String routerDefaultFlowId; // 仅用于路由分支节点 BpmSimpleModelNodeType.ROUTER_BRANCH_NODE

    /**
     * 触发器节点设置
     */
    private TriggerSetting triggerSetting;

    /**
     * 任务监听器
     */
    @Data
    @Valid
    @Schema(description = "任务监听器")
    public static class ListenerHandler {

        /**
         * 是否开启任务监听器
         */
        @NotNull
        @Schema(description = "是否开启任务监听器", example = "false")
        private Boolean enable;

        /**
         * 请求路径
         */
        @Schema(description = "请求路径", example = "http://xxxxx")
        private String path;

        /**
         * 请求头
         */
        @Schema(description = "请求头", example = "[]")
        private List<HttpRequestParam> header;

        /**
         * 请求体
         */
        @Schema(description = "请求体", example = "[]")
        private List<HttpRequestParam> body;

    }

    /**
     * HTTP 请求参数设置
     */
    @Data
    @Schema(description = "HTTP 请求参数设置")
    public static class HttpRequestParam {

        @NotNull
        @Schema(description = "值类型")
        private Integer type;

        @NotEmpty
        @Schema(description = "键")
        private String key;

        @NotEmpty
        @Schema(description = "值")
        private String value;
    }

    /**
     * 审批节点拒绝处理策略
     */
    @Data
    @Schema(description = "审批节点拒绝处理策略")
    public static class RejectHandler {

        /**
         * 拒绝处理类型
         */
        @Schema(description = "拒绝处理类型")
        private Integer type;

        /**
         * 任务拒绝后驳回的节点 Id
         */
        @Schema(description = "任务拒绝后驳回的节点 Id")
        private String returnNodeId;
    }

    /**
     * 审批节点超时处理策略
     */
    @Data
    @Valid
    @Schema(description = "审批节点超时处理策略")
    public static class TimeoutHandler {

        /**
         * 是否开启超时处理
         */
        @NotNull
        @Schema(description = "是否开启超时处理")
        private Boolean enable;

        /**
         * 任务超时未处理的行为
         */
        @NotNull
        @Schema(description = "任务超时未处理的行为")
        private Integer type;

        /**
         * 超时时间
         */
        @Schema(description = "超时时间")
        @NotEmpty(message = "超时时间不能为空")
        private String timeDuration;

        /**
         * 最大提醒次数
         */
        @Schema(description = "最大提醒次数")
        private Integer maxRemindCount;
    }

    /**
     * 空处理策略
     */
    @Data
    @Valid
    @Schema(description = "空处理策略")
    public static class AssignEmptyHandler {

        /**
         * 空处理类型
         */
        @NotNull
        @Schema(description = "空处理类型")
        private Integer type;

        /**
         * 指定人员审批的用户编号数组
         */
        @Schema(description = "指定人员审批的用户编号数组")
        private List<Long> userIds;
    }

    /**
     * 操作按钮设置
     */
    @Data
    @Valid
    @Schema(description = "操作按钮设置")
    public static class OperationButtonSetting {

        /**
         * 按钮 Id
         */
        @Schema(description = "按钮 Id")
        private Integer id;

        /**
         * 显示名称
         */
        @Schema(description = "显示名称")
        private String displayName;

        /**
         * 是否启用
         */
        @Schema(description = "是否启用")
        private Boolean enable;
    }

    /**
     * 条件设置
     * <br>
     * <br>
     * 仅用于条件节点 BpmSimpleModelNodeType.CONDITION_NODE
     */
    @Data
    @Valid
    @Schema(description = "条件设置")
    public static class ConditionSetting {

        /**
         * 条件类型
         */
        @Schema(description = "条件类型")
        private Integer conditionType;

        /**
         * 条件表达式
         */
        @Schema(description = "条件表达式", example = "${day>3}")
        private String conditionExpression;

        /**
         * 是否默认条件
         */
        @Schema(description = "是否默认条件", example = "true")
        private Boolean defaultFlow;

        /**
         * 条件组
         */
        private ConditionGroups conditionGroups;
    }

    /**
     * 条件组
     */
    @Data
    @Valid
    @Schema(description = "条件组")
    public static class ConditionGroups {

        /**
         * 条件组下的条件关系是否为与关系
         */
        @Schema(description = "条件组下的条件关系是否为与关系")
        @NotNull(message = "条件关系不能为空")
        private Boolean and;

        /**
         * 条件组下的条件
         */
        @Schema(description = "条件组下的条件", example = "[]")
        @NotEmpty(message = "条件不能为空")
        private List<Condition> conditions;
    }

    /**
     * 条件
     */
    @Data
    @Valid
    @Schema(description = "条件")
    public static class Condition {

        /**
         * 条件下的规则关系是否为与关系
         */
        @Schema(description = "条件下的规则关系是否为与关系")
        private Boolean and;

        /**
         * 条件下的规则
         */
        @Schema(description = "条件下的规则")
        private List<ConditionRule> rules;
    }

    /**
     * 条件规则
     */
    @Data
    @Schema(description = "条件规则")
    public static class ConditionRule {

        /**
         * 运行符号
         */
        @Schema(description = "运行符号")
        private String opCode;

        /**
         * 运算符左边的值,例如某个流程变量
         */
        @Schema(description = "运算符左边的值,例如某个流程变量")
        private String leftSide;

        /**
         * 运算符右边的值
         */
        @Schema(description = "运算符右边的值")
        private String rightSide;
    }

    /**
     * 延迟器
     */
    @Data
    @Schema(description = "延迟器")
    public static class DelaySetting {

        /**
         * 延迟时间类型
         */
        @NotNull
        @Schema(description = "延迟时间类型", example = "1")
        private Integer delayType;

        /**
         * 延迟时间表达式
         */
        @Schema(description = "延迟时间表达式", example = "PT1H,2025-01-01T00:00:00")
        @NotEmpty
        private String delayTime;
    }

    /**
     * 路由分支
     */
    @Data
    @Valid
    @Schema(description = "路由分支")
    public static class RouterSetting {

        /**
         * 节点 Id
         */
        @Schema(description = "节点 Id", example = "Activity_xxx") // 跳转到该节点
        @NotEmpty
        private String nodeId;

        /**
         * 条件类型
         */
        @Schema(description = "条件类型", example = "1")
        @NotNull
        private Integer conditionType;

        /**
         * 条件表达式
         */
        @Schema(description = "条件表达式", example = "${day>3}")
        private String conditionExpression;

        /**
         * 条件组
         */
        @Schema(description = "条件组", example = "{}")
        private ConditionGroups conditionGroups;
    }

    /**
     * 触发器节点配置
     */
    @Data
    @Valid
    @Schema(description = "触发器节点配置")
    public static class TriggerSetting {

        /**
         * 触发器类型
         */
        @Schema(description = "触发器类型", example = "1")
        @NotNull
        private Integer type;

        /**
         * http 请求触发器设置
         */
        @Valid
        private HttpRequestTriggerSetting httpRequestSetting;

        /**
         * 流程表单触发器设置
         */
        private NormalFormTriggerSetting normalFormSetting;

        /**
         * http 请求触发器设置
         */
        @Data
        @Schema(description = "http 请求触发器设置", example = "{}")
        public static class HttpRequestTriggerSetting {

            /**
             * 请求路径
             */
            @Schema(description = "请求路径", example = "http://127.0.0.1")
            @NotEmpty(message = "请求 URL 不能为空")
            @URL(message = "请求 URL 格式不正确")
            private String url;

            /**
             * 请求头参数设置
             */
            @Schema(description = "请求头参数设置", example = "[]")
            @Valid
            private List<HttpRequestParam> header;

            /**
             * 请求体参数设置
             */
            @Schema(description = "请求头参数设置", example = "[]")
            @Valid
            private List<HttpRequestParam> body;

            /**
             * 请求返回处理设置，用于修改流程表单值
             * <p>
             * key：表示要修改的流程表单字段名(name)
             * value：接口返回的字段名
             */
            @Schema(description = "请求返回处理设置", example = "[]")
            private Map<String, String> response;

        }

        /**
         * 流程表单触发器设置
         */
        @Data
        @Schema(description = "流程表单触发器设置", example = "{}")
        public static class NormalFormTriggerSetting {

            /**
             * 修改的表单字段
             */
            @Schema(description = "修改的表单字段")
            private Map<String, Object> updateFormFields;
        }
    }
}
