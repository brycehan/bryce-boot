package com.brycehan.boot.bpm.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.brycehan.boot.api.system.BpmUserApi;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.bpm.common.FlowableUtils;
import com.brycehan.boot.bpm.entity.po.BpmProcessDefinitionInfo;
import com.brycehan.boot.bpm.entity.vo.BpmUserSimpleBaseVo;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionInfoService;
import com.brycehan.boot.bpm.service.BpmProcessInstanceService;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.bpm.entity.dto.BpmProcessInstanceCopyPageDto;
import com.brycehan.boot.bpm.entity.po.BpmProcessInstanceCopy;
import com.brycehan.boot.bpm.service.BpmProcessInstanceCopyService;
import com.brycehan.boot.bpm.entity.vo.BpmProcessInstanceCopyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.flowable.engine.history.HistoricProcessInstance;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 流程抄送API
 *
 * @author Bryce Han
 * @since 2025/03/25
 */
@Tag(name = "流程抄送", description = "bpmProcessInstanceCopy")
@RequestMapping("/bpm/process-instance/copy")
@RestController
@RequiredArgsConstructor
public class BpmProcessInstanceCopyController {

    @Resource
    private BpmProcessInstanceCopyService processInstanceCopyService;
    @Resource
    private BpmProcessInstanceService processInstanceService;
    @Resource
    private BpmProcessDefinitionInfoService bpmProcessDefinitionInfoService;

    @Resource
    private BpmUserApi adminUserApi;

    /**
     * 流程抄送分页查询
     *
     * @param pageReqVO 查询条件
     * @return 流程抄送分页列表
     */
    @GetMapping("/page")
    @Operation(summary = "获得抄送流程分页列表")
    @PreAuthorize("@auth.hasAuthority('bpm:process-instance:copy-query')")
    public ResponseResult<PageResult<BpmProcessInstanceCopyVo>> getProcessInstanceCopyPage(@Validated BpmProcessInstanceCopyPageDto pageReqVO) {
        pageReqVO.setUserId(LoginUserContext.currentUserId());
        PageResult<BpmProcessInstanceCopy> pageResult = processInstanceCopyService.page(pageReqVO);
        if (CollUtil.isEmpty(pageResult.getList())) {
            return ResponseResult.ok(PageResult.empty());
        }

        // 拼接返回
        Set<String> processInstanceIds = pageResult.getList().stream().map(BpmProcessInstanceCopy::getProcessInstanceId).collect(Collectors.toSet());
        Map<String, HistoricProcessInstance> processInstanceMap = processInstanceService.getHistoricProcessInstanceMap(processInstanceIds);
        List<Long> userIds = pageResult.getList().stream().flatMap(copy -> Stream.of(copy.getStartUserId(), copy.getUserId())).filter(Objects::nonNull)
                .toList();
        Map<Long, BpmUserVo> userMap = adminUserApi.getUserMap(userIds);
        List<String> processDefinitionIds = pageResult.getList().stream().map(BpmProcessInstanceCopy::getProcessDefinitionId).toList();
        Map<String, BpmProcessDefinitionInfo> processDefinitionInfoMap = bpmProcessDefinitionInfoService.getProcessDefinitionInfoMap(processDefinitionIds);

        List<BpmProcessInstanceCopyVo> list = pageResult.getList().stream().map(copy -> {
            BpmProcessInstanceCopyVo copyVO = BeanUtil.toBean(copy, BpmProcessInstanceCopyVo.class);
            Optional.ofNullable(userMap.get(copy.getStartUserId())).ifPresent(bpmUserVo -> copyVO.setStartUser(BeanUtil.toBean(bpmUserVo, BpmUserSimpleBaseVo.class)));
            Optional.ofNullable(userMap.get(copy.getCreatedUserId())).ifPresent(bpmUserVo -> copyVO.setCreateUser(BeanUtil.toBean(bpmUserVo, BpmUserSimpleBaseVo.class)));
            Optional.ofNullable(processInstanceMap.get(copyVO.getProcessInstanceId()))
                    .ifPresent(processInstance -> {
                        copyVO.setSummary(FlowableUtils.getSummary(
                                processDefinitionInfoMap.get(processInstance.getProcessDefinitionId()),
                                processInstance.getProcessVariables()));
                        copyVO.setProcessInstanceStartTime(DateUtil.toLocalDateTime(processInstance.getStartTime()));
                    });

            return copyVO;
        }).toList();

        return ResponseResult.ok(PageResult.of(list, pageResult.getTotal()));
    }

}