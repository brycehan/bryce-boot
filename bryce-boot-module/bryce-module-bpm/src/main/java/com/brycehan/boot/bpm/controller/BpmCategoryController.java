package com.brycehan.boot.bpm.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.bpm.entity.convert.BpmCategoryConvert;
import com.brycehan.boot.bpm.entity.dto.BpmCategoryDto;
import com.brycehan.boot.bpm.entity.dto.BpmCategoryPageDto;
import com.brycehan.boot.bpm.entity.po.BpmCategory;
import com.brycehan.boot.bpm.service.BpmCategoryService;
import com.brycehan.boot.bpm.entity.vo.BpmCategoryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程分类API
 *
 * @author Bryce Han
 * @since 2025/02/23
 */
@Tag(name = "流程分类", description = "bpmCategory")
@RequestMapping("/bpm/category")
@RestController
@RequiredArgsConstructor
public class BpmCategoryController {

    private final BpmCategoryService bpmCategoryService;

    /**
     * 保存流程分类
     *
     * @param bpmCategoryDto 流程分类Dto
     * @return 响应结果
     */
    @Operation(summary = "保存流程分类")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('bpm:category:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody BpmCategoryDto bpmCategoryDto) {
        bpmCategoryService.save(bpmCategoryDto);
        return ResponseResult.ok();
    }

    /**
     * 更新流程分类
     *
     * @param bpmCategoryDto 流程分类Dto
     * @return 响应结果
     */
    @Operation(summary = "更新流程分类")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('bpm:category:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody BpmCategoryDto bpmCategoryDto) {
        bpmCategoryService.update(bpmCategoryDto);
        return ResponseResult.ok();
    }

    /**
     * 删除流程分类
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除流程分类")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('bpm:category:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        bpmCategoryService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询流程分类详情
     *
     * @param id 流程分类ID
     * @return 响应结果
     */
    @Operation(summary = "查询流程分类详情")
    @PreAuthorize("@auth.hasAuthority('bpm:category:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<BpmCategoryVo> get(@Parameter(description = "流程分类ID", required = true) @PathVariable Long id) {
        BpmCategory bpmCategory = bpmCategoryService.getById(id);
        return ResponseResult.ok(BpmCategoryConvert.INSTANCE.convert(bpmCategory));
    }

    /**
     * 流程分类分页查询
     *
     * @param bpmCategoryPageDto 查询条件
     * @return 流程分类分页列表
     */
    @Operation(summary = "流程分类分页查询")
    @PreAuthorize("@auth.hasAuthority('bpm:category:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<BpmCategoryVo>> page(@Validated @RequestBody BpmCategoryPageDto bpmCategoryPageDto) {
        PageResult<BpmCategoryVo> page = bpmCategoryService.page(bpmCategoryPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 流程分类分页查询
     *
     * @param bpmCategoryPageDto 查询条件
     * @return 流程分类分页列表
     */
    @Operation(summary = "流程分类分页查询")
    @PostMapping(path = "/list")
    public ResponseResult<List<BpmCategoryVo>> list(@Validated @RequestBody BpmCategoryPageDto bpmCategoryPageDto) {
        List<BpmCategoryVo> list = bpmCategoryService.list(bpmCategoryPageDto);
        return ResponseResult.ok(list);
    }

    /**
     * 流程分类导出数据
     *
     * @param bpmCategoryPageDto 查询条件
     */
    @Operation(summary = "流程分类导出")
    @PreAuthorize("@auth.hasAuthority('bpm:category:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody BpmCategoryPageDto bpmCategoryPageDto) {
        bpmCategoryService.export(bpmCategoryPageDto);
    }

}