package com.brycehan.boot.bpm.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.*;
import com.brycehan.boot.bpm.common.candidate.BpmTaskCandidateStrategyEnum;
import com.brycehan.boot.bpm.common.listener.BpmCopyTaskDelegate;
import com.brycehan.boot.bpm.common.listener.BpmTriggerTaskDelegate;
import com.brycehan.boot.bpm.common.type.*;
import com.brycehan.boot.bpm.entity.vo.BpmSimpleModelNodeVo;
import org.flowable.bpmn.BpmnAutoLayout;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.*;
import org.flowable.engine.delegate.TaskListener;
import org.springframework.util.MultiValueMap;

import java.util.*;
import static java.util.Arrays.asList;

/**
 * 仿钉钉/飞书的模型相关的工具方法
 * <p>
 * 1. 核心的逻辑实现，可见 {@link #buildBpmnModel(String, String, BpmSimpleModelNodeVo)} 方法
 * 2. 所有的 BpmSimpleModelNodeVo 转换成 BPMN FlowNode 元素，可见 {@link NodeConvert} 实现类
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
public class SimpleModelUtils {

    private static final Map<BpmSimpleModelNodeTypeEnum, NodeConvert> NODE_CONVERTS = MapUtil.newHashMap();

    static {
        List<NodeConvert> converts = asList(new StartNodeConvert(), new EndNodeConvert(),
                new StartUserNodeConvert(), new ApproveNodeConvert(), new CopyNodeConvert(),
                new DelayTimerNodeConvert(), new TriggerNodeConvert(),
                new ConditionBranchNodeConvert(), new ParallelBranchNodeConvert(), new InclusiveBranchNodeConvert(), new RouteBranchNodeConvert());
        converts.forEach(convert -> NODE_CONVERTS.put(convert.getType(), convert));
    }

    /**
     * 仿钉钉流程设计模型数据结构（json）转换成 Bpmn Model
     * <p>
     * 整体逻辑如下：
     * 1. 创建：BpmnModel、Process 对象
     * 2. 转换：将 BpmSimpleModelNodeVo 转换成 BPMN FlowNode 元素
     * 3. 连接：构建并添加节点之间的连线 Sequence Flow
     *
     * @param processId       流程标识
     * @param processName     流程名称
     * @param simpleModelNode 仿钉钉流程设计模型数据结构
     * @return Bpmn Model
     */
    public static BpmnModel buildBpmnModel(String processId, String processName, BpmSimpleModelNodeVo simpleModelNode) {
        // 1. 创建 BpmnModel
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.setTargetNamespace(BpmnXMLConstants.BPMN2_NAMESPACE); // 设置命名空间。不加这个，解析 Message 会报 NPE 异常
        // 创建 Process 对象
        Process process = new Process();
        process.setId(processId);
        process.setName(processName);
        process.setExecutable(Boolean.TRUE);
        bpmnModel.addProcess(process);

        // 2.1 创建 StartNode 节点
        // 原因是：目前前端的第一个节点是“发起人节点”，所以这里构建一个 StartNode，用于创建 Bpmn 的 StartEvent 节点
        BpmSimpleModelNodeVo startNode = buildStartNode();
        startNode.setChildNode(simpleModelNode);
        // 2.2 将前端传递的 simpleModelNode 数据结构（json），转换成从 BPMN FlowNode 元素，并添加到 Main Process 中
        traverseNodeToBuildFlowNode(startNode, process);

        // 3. 构建并添加节点之间的连线 Sequence Flow
        EndEvent endEvent = BpmnModelUtils.getEndEvent(bpmnModel);
        traverseNodeToBuildSequenceFlow(process, startNode, endEvent.getId());

        // 4. 自动布局
        new BpmnAutoLayout(bpmnModel).execute();
        return bpmnModel;
    }

    private static BpmSimpleModelNodeVo buildStartNode() {
        return new BpmSimpleModelNodeVo().setId(BpmnModelConstants.START_EVENT_NODE_ID)
                .setName(BpmSimpleModelNodeTypeEnum.START_NODE.getDesc())
                .setType(BpmSimpleModelNodeTypeEnum.START_NODE.getValue());
    }

    /**
     * 遍历节点，构建 FlowNode 元素
     *
     * @param node    SIMPLE 节点
     * @param process BPMN 流程
     */
    private static void traverseNodeToBuildFlowNode(BpmSimpleModelNodeVo node, Process process) {
        // 1. 判断是否有效节点
        if (!isValidNode(node)) {
            return;
        }
        BpmSimpleModelNodeTypeEnum nodeType = BpmSimpleModelNodeTypeEnum.of(node.getType());
        Assert.notNull(nodeType, "模型节点类型({})不支持", node.getType());

        // 2. 处理当前节点
        NodeConvert nodeConvert = NODE_CONVERTS.get(nodeType);
        Assert.notNull(nodeConvert, "模型节点类型的转换器({})不存在", node.getType());
        List<? extends FlowElement> flowElements = nodeConvert.convertList(node);
        flowElements.forEach(process::addFlowElement);

        // 3.1 情况一：如果当前是分支节点，并且存在条件节点，则处理每个条件的子节点
        if (BpmSimpleModelNodeTypeEnum.isBranchNode(node.getType())
                && CollUtil.isNotEmpty(node.getConditionNodes())) {
            // 注意：这里的 item.getChildNode() 处理的是每个条件的子节点，不是处理条件
            node.getConditionNodes().forEach(item -> traverseNodeToBuildFlowNode(item.getChildNode(), process));
        }

        // 3.2 情况二：如果有“子”节点，则递归处理子节点
        traverseNodeToBuildFlowNode(node.getChildNode(), process);
    }

    /**
     * 遍历节点，构建 SequenceFlow 元素
     *
     * @param process      Bpmn 流程
     * @param node         当前节点
     * @param targetNodeId 目标节点 ID
     */
    private static void traverseNodeToBuildSequenceFlow(Process process, BpmSimpleModelNodeVo node, String targetNodeId) {
        // 1.1 无效节点返回
        if (!isValidNode(node)) {
            return;
        }
        // 1.2 END_NODE 直接返回
        BpmSimpleModelNodeTypeEnum nodeType = BpmSimpleModelNodeTypeEnum.of(node.getType());
        Assert.notNull(nodeType, "模型节点类型不支持");
        if (nodeType == BpmSimpleModelNodeTypeEnum.END_NODE) {
            return;
        }

        // 2.1 情况一：普通节点
        if (!BpmSimpleModelNodeTypeEnum.isBranchNode(node.getType())) {
            traverseNormalNodeToBuildSequenceFlow(process, node, targetNodeId);
        } else {
            // 2.2 情况二：分支节点
            traverseBranchNodeToBuildSequenceFlow(process, node, targetNodeId);
        }
    }

    /**
     * 遍历普通（非条件）节点，构建 SequenceFlow 元素
     *
     * @param process      Bpmn 流程
     * @param node         当前节点
     * @param targetNodeId 目标节点 ID
     */
    private static void traverseNormalNodeToBuildSequenceFlow(Process process, BpmSimpleModelNodeVo node, String targetNodeId) {
        BpmSimpleModelNodeVo childNode = node.getChildNode();
        boolean isChildNodeValid = isValidNode(childNode);
        // 情况一：有“子”节点，则建立连线
        // 情况二：没有“子节点”，则直接跟 targetNodeId 建立连线。例如说，结束节点、条件分支（分支节点的孩子节点或聚合节点）的最后一个节点
        String finalTargetNodeId = isChildNodeValid ? childNode.getId() : targetNodeId;
        SequenceFlow sequenceFlow = buildBpmnSequenceFlow(node.getId(), finalTargetNodeId);
        process.addFlowElement(sequenceFlow);

        // 因为有子节点，递归调用后续子节点
        if (isChildNodeValid) {
            traverseNodeToBuildSequenceFlow(process, childNode, targetNodeId);
        }
    }

    /**
     * 遍历条件节点，构建 SequenceFlow 元素
     *
     * @param process      Bpmn 流程
     * @param node         当前节点
     * @param targetNodeId 目标节点 ID
     */
    private static void traverseBranchNodeToBuildSequenceFlow(Process process, BpmSimpleModelNodeVo node, String targetNodeId) {
        BpmSimpleModelNodeTypeEnum nodeType = BpmSimpleModelNodeTypeEnum.of(node.getType());
        BpmSimpleModelNodeVo childNode = node.getChildNode();
        List<BpmSimpleModelNodeVo> conditionNodes = node.getConditionNodes();
        // TODO 路由分支没有conditionNodes 这里注释会影响吗?
//        Assert.notEmpty(conditionNodes, "分支节点的条件节点不能为空");
        // 分支终点节点 ID
        String branchEndNodeId = null;
        if (nodeType == BpmSimpleModelNodeTypeEnum.CONDITION_BRANCH_NODE
                || nodeType == BpmSimpleModelNodeTypeEnum.ROUTER_BRANCH_NODE) { // 条件分支或路由分支
            // 分两种情况 1. 分支节点有孩子节点为孩子节点 Id 2. 分支节点孩子为无效节点时 (分支嵌套且为分支最后一个节点) 为分支终点节点 ID
            branchEndNodeId = isValidNode(childNode) ? childNode.getId() : targetNodeId;
        } else if (nodeType == BpmSimpleModelNodeTypeEnum.PARALLEL_BRANCH_NODE
                || nodeType == BpmSimpleModelNodeTypeEnum.INCLUSIVE_BRANCH_NODE) {  // 并行分支或包容分支
            // 分支节点：分支终点节点 Id 为程序创建的网关集合节点。目前不会从前端传入。
            branchEndNodeId = buildGatewayJoinId(node.getId());
        }
        Assert.notEmpty(branchEndNodeId, "分支终点节点 Id 不能为空");

        // 3. 遍历分支节点
        if (nodeType == BpmSimpleModelNodeTypeEnum.ROUTER_BRANCH_NODE) {
            // 路由分支遍历
            for (BpmSimpleModelNodeVo.RouterSetting router : node.getRouterGroups()) {
                SequenceFlow sequenceFlow = RouteBranchNodeConvert.buildSequenceFlow(node.getId(), router);
                process.addFlowElement(sequenceFlow);
            }
        } else {
            // 下面的注释，以如下情况举例子。分支 1：A->B->C->D->E，分支 2：A->D->E。其中，A 为分支节点, D 为 A 孩子节点
            for (BpmSimpleModelNodeVo item : conditionNodes) {
                Assert.isTrue(Objects.equals(item.getType(), BpmSimpleModelNodeTypeEnum.CONDITION_NODE.getValue()),
                        "条件节点类型({})不符合", item.getType());
                BpmSimpleModelNodeVo conditionChildNode = item.getChildNode();
                // 3.1 分支有后续节点。即分支 1: A->B->C->D 的情况
                if (isValidNode(conditionChildNode)) {
                    // 3.1.1 建立与后续的节点的连线。例如说，建立 A->B 的连线
                    SequenceFlow sequenceFlow = ConditionNodeConvert.buildSequenceFlow(node.getId(), conditionChildNode.getId(), item);
                    process.addFlowElement(sequenceFlow);
                    // 3.1.2 递归调用后续节点连线。例如说，建立 B->C->D 的连线
                    traverseNodeToBuildSequenceFlow(process, conditionChildNode, branchEndNodeId);
                } else {
                    // 3.2 分支没有后续节点。例如说，建立 A->D 的连线
                    SequenceFlow sequenceFlow = ConditionNodeConvert.buildSequenceFlow(node.getId(), branchEndNodeId, item);
                    process.addFlowElement(sequenceFlow);
                }
            }
        }

        // 4.1 如果是并行分支、包容分支，由于是程序创建的聚合网关，需要手工创建聚合网关和下一个节点的连线
        if (nodeType == BpmSimpleModelNodeTypeEnum.PARALLEL_BRANCH_NODE
                || nodeType == BpmSimpleModelNodeTypeEnum.INCLUSIVE_BRANCH_NODE) {
            String nextNodeId = isValidNode(childNode) ? childNode.getId() : targetNodeId;
            SequenceFlow sequenceFlow = buildBpmnSequenceFlow(branchEndNodeId, nextNodeId);
            process.addFlowElement(sequenceFlow);
            // 4.2 如果是路由分支，需要连接后续节点为默认路由
        } else if (nodeType == BpmSimpleModelNodeTypeEnum.ROUTER_BRANCH_NODE) {
            SequenceFlow sequenceFlow = buildBpmnSequenceFlow(node.getId(), branchEndNodeId, node.getRouterDefaultFlowId(),
                    null, null);
            process.addFlowElement(sequenceFlow);
        }

        // 5. 递归调用后续节点 继续递归。例如说，建立 D->E 的连线
        traverseNodeToBuildSequenceFlow(process, childNode, targetNodeId);
    }

    private static SequenceFlow buildBpmnSequenceFlow(String sourceId, String targetId) {
        return buildBpmnSequenceFlow(sourceId, targetId, null, null, null);
    }

    private static SequenceFlow buildBpmnSequenceFlow(String sourceId, String targetId,
                                                      String sequenceFlowId, String sequenceFlowName,
                                                      String conditionExpression) {
        Assert.notEmpty(sourceId, "sourceId 不能为空");
        Assert.notEmpty(targetId, "targetId 不能为空");
        // TODO 如果 sequenceFlowId 不存在的时候，是不是要生成一个默认的 sequenceFlowId？ 貌似不需要,Flowable 会默认生成；
        //  建议还是搞一个，主要是后续好排查问题。
        // 如果 name 不存在的时候，是不是要生成一个默认的 name？不需要生成默认的吧？
        //  这个会在流程图展示的， 一般用户填写的。不好生成默认的吧；建议还是搞一个，主要是后续好排查问题。
        SequenceFlow sequenceFlow = new SequenceFlow(sourceId, targetId);
        if (StrUtil.isNotEmpty(sequenceFlowId)) {
            sequenceFlow.setId(sequenceFlowId);
        }
        if (StrUtil.isNotEmpty(sequenceFlowName)) {
            sequenceFlow.setName(sequenceFlowName);
        }
        if (StrUtil.isNotEmpty(conditionExpression)) {
            sequenceFlow.setConditionExpression(conditionExpression);
        }
        return sequenceFlow;
    }

    public static boolean isValidNode(BpmSimpleModelNodeVo node) {
        return node != null && node.getId() != null;
    }

    public static boolean isSequentialApproveNode(BpmSimpleModelNodeVo node) {
        return BpmSimpleModelNodeTypeEnum.APPROVE_NODE.getValue().equals(node.getType())
                && BpmUserTaskApproveMethodEnum.SEQUENTIAL.getValue().equals(node.getApproveMethod());
    }

    // ========== 各种 convert 节点的方法: BpmSimpleModelNodeVo => BPMN FlowElement ==========

    private interface NodeConvert {

        default List<? extends FlowElement> convertList(BpmSimpleModelNodeVo node) {
            return Collections.singletonList(convert(node));
        }

        default FlowElement convert(BpmSimpleModelNodeVo node) {
            throw new UnsupportedOperationException("请实现该方法");
        }

        BpmSimpleModelNodeTypeEnum getType();

    }

    private static class StartNodeConvert implements NodeConvert {

        @Override
        public StartEvent convert(BpmSimpleModelNodeVo node) {
            StartEvent startEvent = new StartEvent();
            startEvent.setId(node.getId());
            startEvent.setName(node.getName());
            return startEvent;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.START_NODE;
        }

    }

    private static class EndNodeConvert implements NodeConvert {

        @Override
        public EndEvent convert(BpmSimpleModelNodeVo node) {
            EndEvent endEvent = new EndEvent();
            endEvent.setId(node.getId());
            endEvent.setName(node.getName());
            // TODO 要不要加一个终止定义？
            return endEvent;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.END_NODE;
        }

    }

    private static class StartUserNodeConvert implements NodeConvert {

        @Override
        public UserTask convert(BpmSimpleModelNodeVo node) {
            UserTask userTask = new UserTask();
            userTask.setId(node.getId());
            userTask.setName(node.getName());

            // 人工审批
            BpmnModelUtils.addExtensionElement(userTask, BpmnModelConstants.USER_TASK_APPROVE_TYPE, BpmUserTaskApproveTypeEnum.USER.getValue());
            // 候选人策略为发起人自己
            BpmnModelUtils.addCandidateElements(BpmTaskCandidateStrategyEnum.START_USER.getValue(), null, userTask);
            // 添加表单字段权限属性元素
            BpmnModelUtils.addFormFieldsPermission(node.getFieldsPermission(), userTask);
            // 添加操作按钮配置属性元素
            BpmnModelUtils.addButtonsSetting(node.getButtonsSetting(), userTask);
            // 使用自动通过策略
            // TODO 复用了SKIP， 是否需要新加一个策略：【回复】是不是应该类似飞书，搞个草稿状态。待定；还有一种策略，不标记自动通过，而是首次发起后，第一个节点，自动通过；
            BpmnModelUtils.addAssignStartUserHandlerType(BpmUserTaskAssignStartUserHandlerTypeEnum.SKIP.getValue(), userTask);
            return userTask;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.START_USER_NODE;
        }

    }

    private static class ApproveNodeConvert implements NodeConvert {

        @Override
        public List<FlowElement> convertList(BpmSimpleModelNodeVo node) {
            List<FlowElement> flowElements = new ArrayList<>(2);
            // 1. 构建用户任务
            UserTask userTask = buildBpmnUserTask(node);
            flowElements.add(userTask);

            // 2. 添加用户任务的 Timer Boundary Event, 用于任务的审批超时处理
            if (node.getTimeoutHandler() != null && node.getTimeoutHandler().getEnable()) {
                BoundaryEvent boundaryEvent = buildUserTaskTimeoutBoundaryEvent(userTask, node.getTimeoutHandler());
                flowElements.add(boundaryEvent);
            }
            return flowElements;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.APPROVE_NODE;
        }

        /**
         * 添加 UserTask 用户的审批超时 BoundaryEvent 事件
         *
         * @param userTask       审批任务
         * @param timeoutHandler 超时处理器
         * @return BoundaryEvent 超时事件
         */
        private BoundaryEvent buildUserTaskTimeoutBoundaryEvent(UserTask userTask,
                                                                BpmSimpleModelNodeVo.TimeoutHandler timeoutHandler) {
            // 1.1 定时器边界事件
            BoundaryEvent boundaryEvent = new BoundaryEvent();
            boundaryEvent.setId("Event-" + IdUtil.fastUUID());
            boundaryEvent.setCancelActivity(false); // 设置关联的任务为不会被中断
            boundaryEvent.setAttachedToRef(userTask);
            // 1.2 定义超时时间、最大提醒次数
            TimerEventDefinition eventDefinition = new TimerEventDefinition();
            eventDefinition.setTimeDuration(timeoutHandler.getTimeDuration());
            if (Objects.equals(BpmUserTaskTimeoutHandlerTypeEnum.REMINDER.getValue(), timeoutHandler.getType()) &&
                    timeoutHandler.getMaxRemindCount() != null && timeoutHandler.getMaxRemindCount() > 1) {
                eventDefinition.setTimeCycle(String.format("R%d/%s",
                        timeoutHandler.getMaxRemindCount(), timeoutHandler.getTimeDuration()));
            }
            boundaryEvent.addEventDefinition(eventDefinition);

            // 2.1 添加定时器边界事件类型
            BpmnModelUtils.addExtensionElement(boundaryEvent, BpmnModelConstants.BOUNDARY_EVENT_TYPE, BpmBoundaryEventTypeEnum.USER_TASK_TIMEOUT.getValue());
            // 2.2 添加超时执行动作元素
            BpmnModelUtils.addExtensionElement(boundaryEvent, BpmnModelConstants.USER_TASK_TIMEOUT_HANDLER_TYPE, timeoutHandler.getType());
            return boundaryEvent;
        }

        private UserTask buildBpmnUserTask(BpmSimpleModelNodeVo node) {
            UserTask userTask = new UserTask();
            userTask.setId(node.getId());
            userTask.setName(node.getName());

            // 如果不是审批人节点，则直接返回
            BpmnModelUtils.addExtensionElement(userTask, BpmnModelConstants.USER_TASK_APPROVE_TYPE, node.getApproveType());
            if (ObjectUtil.notEqual(node.getApproveType(), BpmUserTaskApproveTypeEnum.USER.getValue())) {
                return userTask;
            }

            // 添加候选人元素
            BpmnModelUtils.addCandidateElements(node.getCandidateStrategy(), node.getCandidateParam(), userTask);
            // 添加表单字段权限属性元素
            BpmnModelUtils.addFormFieldsPermission(node.getFieldsPermission(), userTask);
            // 添加操作按钮配置属性元素
            BpmnModelUtils.addButtonsSetting(node.getButtonsSetting(), userTask);
            // 处理多实例（审批方式）
            processMultiInstanceLoopCharacteristics(node.getApproveMethod(), node.getApproveRatio(), userTask);
            // 添加任务被拒绝的处理元素
            BpmnModelUtils.addTaskRejectElements(node.getRejectHandler(), userTask);
            // 添加用户任务的审批人与发起人相同时的处理元素
            BpmnModelUtils.addAssignStartUserHandlerType(node.getAssignStartUserHandlerType(), userTask);
            // 添加用户任务的空处理元素
            BpmnModelUtils.addAssignEmptyHandlerType(node.getAssignEmptyHandler(), userTask);
            //  设置审批任务的截止时间
            if (node.getTimeoutHandler() != null && node.getTimeoutHandler().getEnable()) {
                userTask.setDueDate(node.getTimeoutHandler().getTimeDuration());
            }
            // 设置监听器
            addUserTaskListener(node, userTask);
            // 添加是否需要签名
            BpmnModelUtils.addSignEnable(node.getSignEnable(), userTask);
            // 审批意见
            BpmnModelUtils.addReasonRequire(node.getReasonRequire(), userTask);
            return userTask;
        }

        private void addUserTaskListener(BpmSimpleModelNodeVo node, UserTask userTask) {
            List<FlowableListener> flowableListeners = new ArrayList<>(3);
            if (node.getTaskCreateListener() != null
                    && Boolean.TRUE.equals(node.getTaskCreateListener().getEnable())) {
                FlowableListener flowableListener = new FlowableListener();
                flowableListener.setEvent(TaskListener.EVENTNAME_CREATE);
                flowableListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
                flowableListener.setImplementation(BpmUserTaskListener.DELEGATE_EXPRESSION);
                BpmnModelUtils.addListenerConfig(flowableListener, node.getTaskCreateListener());
                flowableListeners.add(flowableListener);
            }
            if (node.getTaskAssignListener() != null
                    && Boolean.TRUE.equals(node.getTaskAssignListener().getEnable())) {
                FlowableListener flowableListener = new FlowableListener();
                flowableListener.setEvent(TaskListener.EVENTNAME_ASSIGNMENT);
                flowableListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
                flowableListener.setImplementation(BpmUserTaskListener.DELEGATE_EXPRESSION);
                BpmnModelUtils.addListenerConfig(flowableListener, node.getTaskAssignListener());
                flowableListeners.add(flowableListener);
            }
            if (node.getTaskCompleteListener() != null
                    && Boolean.TRUE.equals(node.getTaskCompleteListener().getEnable())) {
                FlowableListener flowableListener = new FlowableListener();
                flowableListener.setEvent(TaskListener.EVENTNAME_COMPLETE);
                flowableListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
                flowableListener.setImplementation(BpmUserTaskListener.DELEGATE_EXPRESSION);
                BpmnModelUtils.addListenerConfig(flowableListener, node.getTaskCompleteListener());
                flowableListeners.add(flowableListener);
            }
            if (CollUtil.isNotEmpty(flowableListeners)) {
                userTask.setTaskListeners(flowableListeners);
            }
        }

        private void processMultiInstanceLoopCharacteristics(Integer approveMethod, Integer approveRatio, UserTask userTask) {
            BpmUserTaskApproveMethodEnum approveMethodEnum = BpmUserTaskApproveMethodEnum.of(approveMethod);
            Assert.notNull(approveMethodEnum, "审批方式({})不能为空", approveMethodEnum);
            // 添加审批方式的扩展属性
            BpmnModelUtils.addExtensionElement(userTask, BpmnModelConstants.USER_TASK_APPROVE_METHOD, approveMethod);
            if (approveMethodEnum == BpmUserTaskApproveMethodEnum.RANDOM) {
                // 随机审批，不需要设置多实例属性
                return;
            }

            // 处理多实例审批方式
            MultiInstanceLoopCharacteristics multiInstanceCharacteristics = new MultiInstanceLoopCharacteristics();
            // 设置 collectionVariable。本系统用不到，仅仅为了 Flowable 校验不报错
            multiInstanceCharacteristics.setInputDataItem("${coll_userList}");
            if (approveMethodEnum == BpmUserTaskApproveMethodEnum.ANY) {
                multiInstanceCharacteristics.setCompletionCondition(approveMethodEnum.getCompletionCondition());
                multiInstanceCharacteristics.setSequential(false);
            } else if (approveMethodEnum == BpmUserTaskApproveMethodEnum.SEQUENTIAL) {
                multiInstanceCharacteristics.setCompletionCondition(approveMethodEnum.getCompletionCondition());
                multiInstanceCharacteristics.setSequential(true);
                multiInstanceCharacteristics.setLoopCardinality("1");
            } else if (approveMethodEnum == BpmUserTaskApproveMethodEnum.RATIO) {
                Assert.notNull(approveRatio, "通过比例不能为空");
                multiInstanceCharacteristics.setCompletionCondition(
                        String.format(approveMethodEnum.getCompletionCondition(), String.format("%.2f", approveRatio / 100D)));
                multiInstanceCharacteristics.setSequential(false);
            }
            userTask.setLoopCharacteristics(multiInstanceCharacteristics);
        }

    }

    private static class CopyNodeConvert implements NodeConvert {

        @Override
        public ServiceTask convert(BpmSimpleModelNodeVo node) {
            ServiceTask serviceTask = new ServiceTask();
            serviceTask.setId(node.getId());
            serviceTask.setName(node.getName());
            serviceTask.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
            serviceTask.setImplementation("${" + BpmCopyTaskDelegate.BEAN_NAME + "}");

            // 添加抄送候选人元素
            BpmnModelUtils.addCandidateElements(node.getCandidateStrategy(), node.getCandidateParam(), serviceTask);
            // 添加表单字段权限属性元素
            BpmnModelUtils.addFormFieldsPermission(node.getFieldsPermission(), serviceTask);
            return serviceTask;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.COPY_NODE;
        }

    }

    private static class ConditionBranchNodeConvert implements NodeConvert {

        @Override
        public ExclusiveGateway convert(BpmSimpleModelNodeVo node) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            exclusiveGateway.setId(node.getId());
            // TODO setName

            // 设置默认的序列流（条件）
            BpmSimpleModelNodeVo defaultSeqFlow = CollUtil.findOne(node.getConditionNodes(),
                    item -> BooleanUtil.isTrue(item.getConditionSetting().getDefaultFlow()));
            Assert.notNull(defaultSeqFlow, "条件分支节点({})的默认序列流不能为空", node.getId());
            exclusiveGateway.setDefaultFlow(defaultSeqFlow.getId());
            return exclusiveGateway;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.CONDITION_BRANCH_NODE;
        }

    }

    private static class ParallelBranchNodeConvert implements NodeConvert {

        @Override
        public List<ParallelGateway> convertList(BpmSimpleModelNodeVo node) {
            ParallelGateway parallelGateway = new ParallelGateway();
            parallelGateway.setId(node.getId());
            // TODO ：setName

            // 并行聚合网关由程序创建，前端不需要传入
            ParallelGateway joinParallelGateway = new ParallelGateway();
            joinParallelGateway.setId(buildGatewayJoinId(node.getId()));
            // TODO ：setName
            return CollUtil.newArrayList(parallelGateway, joinParallelGateway);
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.PARALLEL_BRANCH_NODE;
        }

    }

    private static class InclusiveBranchNodeConvert implements NodeConvert {

        @Override
        public List<InclusiveGateway> convertList(BpmSimpleModelNodeVo node) {
            InclusiveGateway inclusiveGateway = new InclusiveGateway();
            inclusiveGateway.setId(node.getId());
            // 设置默认的序列流（条件）
            BpmSimpleModelNodeVo defaultSeqFlow = CollUtil.findOne(node.getConditionNodes(),
                    item -> BooleanUtil.isTrue(item.getConditionSetting().getDefaultFlow()));
            Assert.notNull(defaultSeqFlow, "包容分支节点({})的默认序列流不能为空", node.getId());
            inclusiveGateway.setDefaultFlow(defaultSeqFlow.getId());
            // TODO ：setName

            // 并行聚合网关由程序创建，前端不需要传入
            InclusiveGateway joinInclusiveGateway = new InclusiveGateway();
            joinInclusiveGateway.setId(buildGatewayJoinId(node.getId()));
            // TODO ：setName
            return CollUtil.newArrayList(inclusiveGateway, joinInclusiveGateway);
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.INCLUSIVE_BRANCH_NODE;
        }

    }

    public static class ConditionNodeConvert implements NodeConvert {

        @Override
        public List<? extends FlowElement> convertList(BpmSimpleModelNodeVo node) {
            // 原因是：正常情况下，它不会被调用到
            throw new UnsupportedOperationException("条件节点不支持转换");
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.CONDITION_NODE;
        }

        public static SequenceFlow buildSequenceFlow(String sourceId, String targetId,
                                                     BpmSimpleModelNodeVo node) {
            String conditionExpression = buildConditionExpression(node.getConditionSetting());
            return buildBpmnSequenceFlow(sourceId, targetId, node.getId(), node.getName(), conditionExpression);
        }
    }

    /**
     * 构造条件表达式
     */
    public static String buildConditionExpression(BpmSimpleModelNodeVo.ConditionSetting conditionSetting) {
        return buildConditionExpression(conditionSetting.getConditionType(), conditionSetting.getConditionExpression(),
                conditionSetting.getConditionGroups());
    }

    public static String buildConditionExpression(BpmSimpleModelNodeVo.RouterSetting routerSetting) {
        return buildConditionExpression(routerSetting.getConditionType(), routerSetting.getConditionExpression(),
                routerSetting.getConditionGroups());
    }

    public static String buildConditionExpression(Integer conditionType, String conditionExpression, BpmSimpleModelNodeVo.ConditionGroups conditionGroups) {
        BpmSimpleModeConditionTypeEnum conditionTypeEnum = BpmSimpleModeConditionTypeEnum.of(conditionType);
        if (conditionTypeEnum == BpmSimpleModeConditionTypeEnum.EXPRESSION) {
            return conditionExpression;
        }
        if (conditionTypeEnum == BpmSimpleModeConditionTypeEnum.RULE) {
            if (conditionGroups == null || CollUtil.isEmpty(conditionGroups.getConditions())) {
                return null;
            }
            List<String> strConditionGroups = conditionGroups.getConditions().stream().map(item -> {
                if (CollUtil.isEmpty(item.getRules())) {
                    return "";
                }
                // 构造规则表达式
                List<String> list = item.getRules().stream().map(rule -> {
                    String rightSide = NumberUtil.isNumber(rule.getRightSide()) ? rule.getRightSide()
                            : "\"" + rule.getRightSide() + "\""; // 如果非数值类型加引号
                    return String.format(" %s %s var:convertByType(%s,%s)", rule.getLeftSide(), rule.getOpCode(), rule.getLeftSide(), rightSide);
                }).toList();
                // 构造条件组的表达式
                Boolean and = item.getAnd();
                return "(" + CollUtil.join(list, and ? " && " : " || ") + ")";
            }).toList();
            return String.format("${%s}", CollUtil.join(strConditionGroups, conditionGroups.getAnd() ? " && " : " || "));
        }
        return null;
    }

    public static class DelayTimerNodeConvert implements NodeConvert {

        @Override
        public List<FlowElement> convertList(BpmSimpleModelNodeVo node) {
            List<FlowElement> flowElements = new ArrayList<>(2);
            // 1. 构建接收任务，通过接收任务可卡住节点
            ReceiveTask receiveTask = new ReceiveTask();
            receiveTask.setId(node.getId());
            receiveTask.setName(node.getName());
            flowElements.add(receiveTask);

            // 2. 添加接收任务的 Timer Boundary Event
            if (node.getDelaySetting() != null) {
                // 2.1 定时器边界事件
                BoundaryEvent boundaryEvent = new BoundaryEvent();
                boundaryEvent.setId("Event-" + IdUtil.fastUUID());
                boundaryEvent.setCancelActivity(false);
                boundaryEvent.setAttachedToRef(receiveTask);
                // 2.2 定义超时时间
                TimerEventDefinition eventDefinition = new TimerEventDefinition();
                if (node.getDelaySetting().getDelayType().equals(BpmDelayTimerTypeEnum.FIXED_DATE_TIME.getValue())) {
                    eventDefinition.setTimeDuration(node.getDelaySetting().getDelayTime());
                } else if (node.getDelaySetting().getDelayType().equals(BpmDelayTimerTypeEnum.FIXED_TIME_DURATION.getValue())) {
                    eventDefinition.setTimeDate(node.getDelaySetting().getDelayTime());
                }
                boundaryEvent.addEventDefinition(eventDefinition);
                BpmnModelUtils.addExtensionElement(boundaryEvent, BpmnModelConstants.BOUNDARY_EVENT_TYPE, BpmBoundaryEventTypeEnum.DELAY_TIMER_TIMEOUT.getValue());
                flowElements.add(boundaryEvent);
            }
            return flowElements;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.DELAY_TIMER_NODE;
        }
    }

    public static class TriggerNodeConvert implements NodeConvert {

        @Override
        public ServiceTask convert(BpmSimpleModelNodeVo node) {
            // 触发器使用 ServiceTask 来实现
            ServiceTask serviceTask = new ServiceTask();
            serviceTask.setId(node.getId());
            serviceTask.setName(node.getName());
            serviceTask.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
            serviceTask.setImplementation("${" + BpmTriggerTaskDelegate.BEAN_NAME + "}");
            if (node.getTriggerSetting() != null) {
                BpmnModelUtils.addExtensionElement(serviceTask, BpmnModelConstants.TRIGGER_TYPE, node.getTriggerSetting().getType());
                if (node.getTriggerSetting().getHttpRequestSetting() != null) {
                    BpmnModelUtils.addExtensionElementJson(serviceTask, BpmnModelConstants.TRIGGER_PARAM, node.getTriggerSetting().getHttpRequestSetting());
                }
                if (node.getTriggerSetting().getNormalFormSetting() != null) {
                    BpmnModelUtils.addExtensionElementJson(serviceTask, BpmnModelConstants.TRIGGER_PARAM, node.getTriggerSetting().getNormalFormSetting());
                }
            }
            return serviceTask;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.TRIGGER_NODE;
        }
    }

    public static class RouteBranchNodeConvert implements NodeConvert {

        @Override
        public ExclusiveGateway convert(BpmSimpleModelNodeVo node) {
            ExclusiveGateway exclusiveGateway = new ExclusiveGateway();
            exclusiveGateway.setId(node.getId());

            // 设置默认的序列流（条件）
            node.setRouterDefaultFlowId("Flow_" + IdUtil.fastUUID());
            exclusiveGateway.setDefaultFlow(node.getRouterDefaultFlowId());
            return exclusiveGateway;
        }

        @Override
        public BpmSimpleModelNodeTypeEnum getType() {
            return BpmSimpleModelNodeTypeEnum.ROUTER_BRANCH_NODE;
        }

        public static SequenceFlow buildSequenceFlow(String nodeId, BpmSimpleModelNodeVo.RouterSetting router) {
            String conditionExpression = SimpleModelUtils.buildConditionExpression(router);
            return buildBpmnSequenceFlow(nodeId, router.getNodeId(), null, null, conditionExpression);
        }

    }

    private static String buildGatewayJoinId(String id) {
        return id + "_join";
    }

    // ========== SIMPLE 流程预测相关的方法 ==========

    public static List<BpmSimpleModelNodeVo> simulateProcess(BpmSimpleModelNodeVo rootNode, Map<String, Object> variables) {
        List<BpmSimpleModelNodeVo> resultNodes = new ArrayList<>();

        // 从头开始遍历
        simulateNextNode(rootNode, variables, resultNodes);
        return resultNodes;
    }

    private static void simulateNextNode(BpmSimpleModelNodeVo currentNode, Map<String, Object> variables,
                                         List<BpmSimpleModelNodeVo> resultNodes) {
        // 如果不合法（包括为空），则直接结束
        if (!isValidNode(currentNode)) {
            return;
        }
        BpmSimpleModelNodeTypeEnum nodeType = BpmSimpleModelNodeTypeEnum.of(currentNode.getType());
        Assert.notNull(nodeType, "模型节点类型不支持");

        // 情况：START_NODE/START_USER_NODE/APPROVE_NODE/COPY_NODE/END_NODE
        if (nodeType == BpmSimpleModelNodeTypeEnum.START_NODE
                || nodeType == BpmSimpleModelNodeTypeEnum.START_USER_NODE
                || nodeType == BpmSimpleModelNodeTypeEnum.APPROVE_NODE
                || nodeType == BpmSimpleModelNodeTypeEnum.COPY_NODE
                || nodeType == BpmSimpleModelNodeTypeEnum.END_NODE) {
            // 添加元素
            resultNodes.add(currentNode);
        }

        // 情况：CONDITION_BRANCH_NODE 排它，只有一个满足条件的。如果没有，就走默认的
        if (nodeType == BpmSimpleModelNodeTypeEnum.CONDITION_BRANCH_NODE) {
            // 查找满足条件的 BpmSimpleModelNodeVo 节点
            BpmSimpleModelNodeVo matchConditionNode = CollUtil.findOne(currentNode.getConditionNodes(),
                    conditionNode -> !BooleanUtil.isTrue(conditionNode.getConditionSetting().getDefaultFlow())
                            && evalConditionExpress(variables, conditionNode.getConditionSetting()));
            if (matchConditionNode == null) {
                matchConditionNode = CollUtil.findOne(currentNode.getConditionNodes(),
                        conditionNode -> BooleanUtil.isTrue(conditionNode.getConditionSetting().getDefaultFlow()));
            }
            Assert.notNull(matchConditionNode, "找不到条件节点({})", currentNode);
            // 遍历满足条件的 BpmSimpleModelNodeVo 节点
            simulateNextNode(matchConditionNode.getChildNode(), variables, resultNodes);
        }

        // 情况：INCLUSIVE_BRANCH_NODE 包容，多个满足条件的。如果没有，就走默认的
        if (nodeType == BpmSimpleModelNodeTypeEnum.INCLUSIVE_BRANCH_NODE) {
            // 查找满足条件的 BpmSimpleModelNodeVo 节点
            Collection<BpmSimpleModelNodeVo> matchConditionNodes = CollUtil.filterNew(currentNode.getConditionNodes(),
                    conditionNode -> !BooleanUtil.isTrue(conditionNode.getConditionSetting().getDefaultFlow())
                            && evalConditionExpress(variables, conditionNode.getConditionSetting()));
            if (CollUtil.isEmpty(matchConditionNodes)) {
                matchConditionNodes = CollUtil.filterNew(currentNode.getConditionNodes(),
                        conditionNode -> BooleanUtil.isTrue(conditionNode.getConditionSetting().getDefaultFlow()));
            }
            Assert.isTrue(!matchConditionNodes.isEmpty(), "找不到条件节点({})", currentNode);
            // 遍历满足条件的 BpmSimpleModelNodeVo 节点
            matchConditionNodes.forEach(matchConditionNode ->
                    simulateNextNode(matchConditionNode.getChildNode(), variables, resultNodes));
        }

        // 情况：PARALLEL_BRANCH_NODE 并行，都满足，都走
        if (nodeType == BpmSimpleModelNodeTypeEnum.PARALLEL_BRANCH_NODE) {
            // 遍历所有 BpmSimpleModelNodeVo 节点
            currentNode.getConditionNodes().forEach(matchConditionNode ->
                    simulateNextNode(matchConditionNode.getChildNode(), variables, resultNodes));
        }

        // 遍历子节点
        simulateNextNode(currentNode.getChildNode(), variables, resultNodes);
    }

    public static boolean evalConditionExpress(Map<String, Object> variables, BpmSimpleModelNodeVo.ConditionSetting conditionSetting) {
        return BpmnModelUtils.evalConditionExpress(variables, buildConditionExpression(conditionSetting));
    }

    /**
     * 添加 HTTP 请求参数。请求头或者请求体
     *
     * @param params           HTTP 请求参数
     * @param paramSettings    HTTP 请求参数设置
     * @param processVariables 流程变量
     */
    public static void addHttpRequestParam(MultiValueMap<String, String> params,
                                           List<BpmSimpleModelNodeVo.HttpRequestParam> paramSettings,
                                           Map<String, Object> processVariables) {
        if (CollUtil.isEmpty(paramSettings)) {
            return;
        }
        paramSettings.forEach(item -> {
            if (item.getType().equals(BpmHttpRequestParamTypeEnum.FIXED_VALUE.getValue())) {
                params.add(item.getKey(), item.getValue());
            } else if (item.getType().equals(BpmHttpRequestParamTypeEnum.FROM_FORM.getValue())) {
                params.add(item.getKey(), processVariables.get(item.getValue()).toString());
            }
        });
    }
}
