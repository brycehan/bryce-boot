package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.bpm.entity.dto.BpmProcessDefinitionPageDto;
import com.brycehan.boot.bpm.entity.vo.BpmProcessDefinitionVo;
import com.brycehan.boot.bpm.service.BpmProcessDefinitionService;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.entity.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义API
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Tag(name = "流程定义", description = "bpmProcessDefinition")
@RequestMapping("/bpm/process-definition")
@RestController
@RequiredArgsConstructor
public class BpmProcessDefinitionController {

    private final BpmProcessDefinitionService bpmProcessDefinitionService;

    /**
     * 查询流程定义详情
     *
     * @param id 流程定义ID
     * @return 响应结果
     */
    @Operation(summary = "查询流程定义信息详情")
    @PreAuthorize("@auth.hasAnyAuthority('bpm:process-definition:query', 'bpm:model:query')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmProcessDefinitionVo> get(@PathVariable String id) {
        BpmProcessDefinitionVo bpmProcessDefinition = bpmProcessDefinitionService.getById(id);
        return ResponseResult.ok(bpmProcessDefinition);
    }

    /**
     * 流程定义分页查询，用于流程模型历史分页查询
     *
     * @param bpmProcessDefinitionPageDto 查询条件
     * @return 流程定义分页列表
     */
    @Operation(summary = "流程定义分页查询，用于流程模型历史分页查询")
    @PreAuthorize("@auth.hasAnyAuthority('bpm:process-definition:query', 'bpm:model:query')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmProcessDefinitionVo>> page(@Validated @RequestBody BpmProcessDefinitionPageDto bpmProcessDefinitionPageDto) {
        PageResult<BpmProcessDefinitionVo> page = bpmProcessDefinitionService.page(bpmProcessDefinitionPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 流程定义列表查询
     *
     * @param bpmProcessDefinitionPageDto 查询条件
     * @return 流程定义分页列表
     */
    @Operation(summary = "流程定义列表查询")
    @PreAuthorize("@auth.hasAuthority('bpm:process-definition:list')")
    @PostMapping(path = "/list")
    public ResponseResult<List<BpmProcessDefinitionVo>> list(@Validated @RequestBody BpmProcessDefinitionPageDto bpmProcessDefinitionPageDto) {
        List<BpmProcessDefinitionVo> list = bpmProcessDefinitionService.list(bpmProcessDefinitionPageDto);
        return ResponseResult.ok(list);
    }

}