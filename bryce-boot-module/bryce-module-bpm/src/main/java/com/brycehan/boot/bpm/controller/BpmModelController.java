package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.bpm.entity.dto.BpmModelDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelKeyDto;
import com.brycehan.boot.bpm.entity.dto.BpmModelPageDto;
import com.brycehan.boot.bpm.entity.vo.BpmModelVo;
import com.brycehan.boot.bpm.service.BpmModelService;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.NotEmptyElements;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程模型API
 *
 * @author Bryce Han
 * @since 2025/02/24
 */
@Validated
@RequiredArgsConstructor
@Tag(name = "流程模型", description = "bpmModel")
@RestController
@RequestMapping("/bpm/model")
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
    public ResponseResult<String> save(@Validated(value = SaveGroup.class) @RequestBody BpmModelDto bpmModelDto) {
        String modelId = bpmModelService.save(bpmModelDto);
        return ResponseResult.ok(modelId);
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

    /**
     * 删除模型
     *
     * @param ids ID列表
     * @return 响应结果
     */
    @Operation(summary = "删除模型")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('bpm:model:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@NotEmptyElements @Size(min = 1, max = 100) @RequestBody List<String> ids) {
        bpmModelService.delete(ids);
        return ResponseResult.ok();
    }

    /**
     * 查询模型详情
     *
     * @param id 模型ID
     * @return 响应结果
     */
    @Operation(summary = "查询模型详情")
    @PreAuthorize("@auth.hasAuthority('bpm:model:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmModelVo> get(@PathVariable String id) {
        BpmModelVo bpmModelVo = bpmModelService.getById(id);
        return ResponseResult.ok(bpmModelVo);
    }

    /**
     * 流程模型分页查询
     *
     * @param bpmModelPageDto 查询条件
     * @return 流程模型分页列表
     */
    @Operation(summary = "流程模型分页列表")
    @PreAuthorize("@auth.hasAuthority('bpm:model:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmModelVo>> page(@Validated @RequestBody BpmModelPageDto bpmModelPageDto) {
        PageResult<BpmModelVo> page = bpmModelService.page(bpmModelPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 部署流程模型
     *
     * @param id 模型ID
     * @return 结果
     */
    @Operation(summary = "部署流程模型")
    @PreAuthorize("@auth.hasAuthority('bpm:model:deploy')")
    @PostMapping(path = "/deploy")
    public ResponseResult<?> deploy(@NotEmpty String id) {
        bpmModelService.deploy(id);
        return ResponseResult.ok();
    }


    /**
     * 修改模型的状态，实际是更新对应的部署的流程定义的状态
     *
     * @param id 模型ID
     * @return 结果
     */
    @Operation(summary = "修改模型的状态")
    @PreAuthorize("@auth.hasAuthority('bpm:model:update')")
    @PatchMapping(path = "/{id}/{state}")
    public ResponseResult<?> updateState(@PathVariable String id, @PathVariable Integer state) {
        bpmModelService.updateState(id, state);
        return ResponseResult.ok();
    }

    /**
     * 校验key是否可用
     *
     * @param bpmModelKeyDto Bpm 模型 Dto
     * @return 响应结果，key是否可用
     */
    @Operation(summary = "校验手机号码是否可注册（true：可用，false：不可以）")
    @GetMapping(path = "/checkKeyUnique")
    public ResponseResult<Boolean> checkKeyUnique(@Validated BpmModelKeyDto bpmModelKeyDto) {
        boolean checked = bpmModelService.checkKeyUnique(bpmModelKeyDto);
        return ResponseResult.ok(checked);
    }
}