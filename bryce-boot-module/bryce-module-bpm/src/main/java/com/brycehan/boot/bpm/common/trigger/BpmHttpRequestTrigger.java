package com.brycehan.boot.bpm.common.trigger;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.bpm.common.FlowableUtils;
import com.brycehan.boot.bpm.common.SimpleModelUtils;
import com.brycehan.boot.bpm.common.type.BpmTriggerTypeEnum;
import com.brycehan.boot.bpm.entity.vo.BpmSimpleModelNodeVo;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.brycehan.boot.common.base.response.HttpResponseStatus;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.util.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * BPM 发送 HTTP 请求触发器
 *
 * @since 2025/3/14
 * @author Bryce Han
 */
@Component
@Slf4j
public class BpmHttpRequestTrigger implements BpmTrigger {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    private final RestClient restClient = RestClient.builder().build();

    @Override
    public BpmTriggerTypeEnum getType() {
        return BpmTriggerTypeEnum.HTTP_REQUEST;
    }

    @Override
    public void execute(String processInstanceId, String param) {
        // 1. 解析 http 请求配置
        BpmSimpleModelNodeVo.TriggerSetting.HttpRequestTriggerSetting setting = JsonUtils.readValue(param, BpmSimpleModelNodeVo.TriggerSetting.HttpRequestTriggerSetting.class);
        if (setting == null) {
            log.error("[execute][流程({}) HTTP 触发器请求配置为空]", processInstanceId);
            return;
        }
        // 2.1 设置请求头
        ProcessInstance processInstance = processInstanceService.getProcessInstance(processInstanceId);
        Map<String, Object> processVariables = processInstance.getProcessVariables();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(FlowableUtils.HEADER_TENANT_ID, processInstance.getTenantId());
        SimpleModelUtils.addHttpRequestParam(headers, setting.getHeader(), processVariables);
        // 2.2 设置请求体
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        SimpleModelUtils.addHttpRequestParam(body, setting.getBody(), processVariables);
        body.add("processInstanceId", processInstanceId);

        // 3. 发起请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> responseEntity;
        try {
            responseEntity = restClient.post()
                    .uri(setting.getUrl())
                    .headers(httpHeaders -> httpHeaders.addAll(headers)) // 设置请求头
                    .body(body) // 设置请求体
                    .retrieve()
                    .toEntity(String.class); // 获取ResponseEntity<String>
            log.info("[execute][HTTP 触发器，请求头：{}，请求体：{}，响应结果：{}]", headers, body, responseEntity);
        } catch (RestClientException e) {
            log.error("[execute][HTTP 触发器，请求头：{}，请求体：{}，请求出错：{}]", headers, body, e.getMessage());
            return;
        }

        // 4.1 判断是否需要解析返回值
        if (StrUtil.isEmpty(responseEntity.getBody())
                || !responseEntity.getStatusCode().is2xxSuccessful()
                || CollUtil.isEmpty(setting.getResponse())) {
            return;
        }
        // 4.2 解析返回值, 返回值必须符合 CommonResult 规范。
        ResponseResult<Map<String, Object>> respResult = JsonUtils.readValue(responseEntity.getBody(), new TypeReference<>() {});
        if (respResult == null || !Objects.equals(respResult.getCode(), HttpResponseStatus.HTTP_OK.code())){
            return;
        }
        // 4.3 获取需要更新的流程变量
        Map<String, Object> updateVariables = getNeedUpdatedVariablesFromResponse(respResult.getData(), setting.getResponse());
        // 4.4 更新流程变量
        if (CollUtil.isNotEmpty(updateVariables)) {
            processInstanceService.updateProcessInstanceVariables(processInstanceId, updateVariables);
        }
    }

    /**
     * 从请求返回值获取需要更新的流程变量
     *
     * @param result 请求返回结果
     * @param responseSettings 返回设置
     * @return 需要更新的流程变量
     */
    private Map<String, Object> getNeedUpdatedVariablesFromResponse(Map<String,Object> result,
                                                                    List<Pair<String, String>> responseSettings) {
        Map<String, Object> updateVariables = new HashMap<>();
        if (CollUtil.isEmpty(result)) {
            return updateVariables;
        }
        responseSettings.forEach(responseSetting -> {
            if (StrUtil.isNotEmpty(responseSetting.getKey()) && result.containsKey(responseSetting.getValue())) {
                updateVariables.put(responseSetting.getKey(), result.get(responseSetting.getValue()));
            }
        });
        return updateVariables;
    }

}
