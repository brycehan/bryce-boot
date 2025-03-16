package com.brycehan.boot.bpm.common;

import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.entity.vo.BpmSimpleModelNodeVo;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import jakarta.annotation.Resource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.TaskListener;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.el.FixedValue;
import org.flowable.task.service.delegate.DelegateTask;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Map;

/**
 * BPM 用户任务通用监听器
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Component
@Slf4j
@Scope("prototype")
public class BpmUserTaskListener implements TaskListener {

    public static final String DELEGATE_EXPRESSION = "${bpmUserTaskListener}";

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @Setter
    private FixedValue listenerConfig;

    private final RestClient restClient = RestClient.builder().build();

    @Override
    public void notify(DelegateTask delegateTask) {
        // 1. 获取所需基础信息
        HistoricProcessInstance processInstance = processInstanceService.getHistoricProcessInstance(delegateTask.getProcessInstanceId());
        BpmSimpleModelNodeVo.ListenerHandler listenerHandler = BpmnModelUtils.parseListenerConfig(listenerConfig);

        // 2. 获取请求头和请求体
        Map<String, Object> processVariables = processInstance.getProcessVariables();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        SimpleModelUtils.addHttpRequestParam(headers, listenerHandler.getHeader(), processVariables);
        SimpleModelUtils.addHttpRequestParam(body, listenerHandler.getBody(), processVariables);
        // 2.1 请求头默认参数
        if (StrUtil.isNotEmpty(delegateTask.getTenantId())) {
            headers.add(FlowableUtils.HEADER_TENANT_ID, delegateTask.getTenantId());
        }
        // 2.2 请求体默认参数
        // TODO：哪些默认参数，后续再调研下；感觉可以搞个 task 字段，把整个 delegateTask 放进去；
        body.add("processInstanceId", delegateTask.getProcessInstanceId());
        body.add("assignee", delegateTask.getAssignee());
        body.add("taskDefinitionKey", delegateTask.getTaskDefinitionKey());
        body.add("taskId", delegateTask.getId());

        // 3. 异步发起请求
        try {
            ResponseEntity<String> responseEntity = restClient.post()
                    .uri(listenerHandler.getPath())
                    .headers(httpHeaders -> httpHeaders.addAll(headers)) // 设置请求头
                    .body(body) // 设置请求体
                    .retrieve()
                    .toEntity(String.class); // 获取ResponseEntity<String>
            log.info("[notify][监听器：{}，事件类型：{}，请求头：{}，请求体：{}，响应结果：{}]",
                    DELEGATE_EXPRESSION,
                    delegateTask.getEventName(),
                    headers,
                    body,
                    responseEntity);
        } catch (RestClientException e) {
            log.error("[error][监听器：{}，事件类型：{}，请求头：{}，请求体：{}，请求出错：{}]",
                    DELEGATE_EXPRESSION,
                    delegateTask.getEventName(),
                    headers,
                    body,
                    e.getMessage());
        }
        // TODO 4. 是否需要后续操作！
    }
}