package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.service.BpmModelService;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 流程模型API
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Tag(name = "流程模型", description = "bpmModel")
@RequestMapping("/bpm/model")
@RestController
@RequiredArgsConstructor
public class BpmModelController {

    private final BpmModelService bpmModelService;

    /**
     * 保存流程模型信息
     *
     * @param bpmModelDto 流程模型Dto
     * @return 响应结果
     */
    @Operation(summary = "保存流程定义信息")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('bpm:model:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody BpmModelDto bpmModelDto) {
        bpmModelService.save(bpmModelDto);
        return ResponseResult.ok();
    }

    /**
     * 更新流程模型
     *
     * @param bpmModelDto 流程模型Dto
     * @return 响应结果
     */
    @Operation(summary = "更新流程定义信息")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('bpm:model:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody BpmModelDto bpmModelDto) {
        bpmModelService.update(bpmModelDto);
        return ResponseResult.ok();
    }

}