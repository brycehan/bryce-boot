package com.brycehan.boot.bpm.common;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.*;
import org.flowable.bpmn.model.Process;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;

import java.util.ArrayList;
import java.util.List;

/**
 * BpmnModel工具类
 *
 * @author Bryce Han
 * @since 2025/3/3
 */
public class BpmnModelUtils {

    /**
     * 获取BpmnModel
     *
     * @param bpmnBytes bpmnBytes
     * @return BpmnModel
     */
    public static BpmnModel getBpmnModel(byte[] bpmnBytes) {
        if (ArrayUtil.isEmpty(bpmnBytes)) {
            return null;
        }

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        // 补充说明：由于在 Flowable 中自定义了属性，所以 validateSchema 传递 false
        return bpmnXMLConverter.convertToBpmnModel(new BytesStreamSource(bpmnBytes), true, true);
    }

    /**
     * 获取BpmnModel
     *
     * @param bpmnModel bpmn模型
     * @return BpmnModel
     */
    public static String getBpmnModel(BpmnModel bpmnModel) {
        if (bpmnModel == null) {
            return null;
        }

        BpmnXMLConverter bpmnXMLConverter = new BpmnXMLConverter();
        return StrUtil.utf8Str(bpmnXMLConverter.convertToXML(bpmnModel));
    }

    /**
     * 获取开始事件
     *
     * @param bpmnModel bpmnModel
     * @return StartEvent
     */
    public static StartEvent getStartEvent(BpmnModel bpmnModel) {
        Process process = bpmnModel.getMainProcess();
        // 从 initialFlowElement 找
        FlowElement initialFlowElement = process.getInitialFlowElement();
        if (initialFlowElement instanceof StartEvent) {
            return (StartEvent) initialFlowElement;
        }

        return (StartEvent) CollUtil.findOne(process.getFlowElements(), flowElement -> flowElement instanceof StartEvent);
    }

    /**
     * 获取BPMN 流程中，指定类型的元素集合
     *
     * @param bpmnModel bpmnModel
     * @param clazz     clazz，例如 UserTask、Gateway等
     * @param <T>       T
     * @return List
     */
    public static <T extends FlowElement> List<T> getFlowElementsOfType(BpmnModel bpmnModel, Class<T> clazz) {
        List<T> flowElements = new ArrayList<>();
        bpmnModel.getProcesses().forEach(process -> flowElements.addAll(process.findFlowElementsOfType(clazz)));
        return flowElements;
    }
}
