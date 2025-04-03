package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.convert.SysPostConvert;
import com.brycehan.boot.system.entity.dto.SysPostCodeDto;
import com.brycehan.boot.system.entity.dto.SysPostDto;
import com.brycehan.boot.system.entity.dto.SysPostPageDto;
import com.brycehan.boot.system.entity.po.SysPost;
import com.brycehan.boot.system.entity.vo.SysPostVo;
import com.brycehan.boot.system.service.SysPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统岗位API
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Tag(name = "系统岗位")
@RequestMapping("/system/post")
@RestController
@RequiredArgsConstructor
public class SysPostController {

    private final SysPostService sysPostService;

    /**
     * 保存系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统岗位")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('system:post:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysPostDto sysPostDto) {
        sysPostService.save(sysPostDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统岗位")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('system:post:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysPostDto sysPostDto) {
        sysPostService.update(sysPostDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统岗位
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统岗位")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('system:post:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        sysPostService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统岗位详情
     *
     * @param id 系统岗位ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统岗位详情")
    @PreAuthorize("@auth.hasAuthority('system:post:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysPostVo> get(@Parameter(description = "系统岗位ID", required = true) @PathVariable Long id) {
        SysPost sysPost = sysPostService.getById(id);
        return ResponseResult.ok(SysPostConvert.INSTANCE.convert(sysPost));
    }

    /**
     * 系统岗位分页查询
     *
     * @param sysPostPageDto 查询条件
     * @return 系统岗位分页列表
     */
    @Operation(summary = "系统岗位分页查询")
    @PreAuthorize("@auth.hasAuthority('system:post:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysPostVo>> page(@Validated @RequestBody SysPostPageDto sysPostPageDto) {
        PageResult<SysPostVo> page = sysPostService.page(sysPostPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统岗位导出数据
     *
     * @param sysPostPageDto 查询条件
     */
    @Operation(summary = "系统岗位导出")
    @PreAuthorize("@auth.hasAuthority('system:post:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysPostPageDto sysPostPageDto) {
        sysPostService.export(sysPostPageDto);
    }

    /**
     * 查询系统岗位列表
     *
     * @return 响应结果
     */
    @Operation(summary = "查询系统岗位列表")
    @GetMapping(path = "/simple-list")
    public ResponseResult<List<SysPostVo>> simpleList() {
        List<SysPostVo> list = sysPostService.simpleList(new SysPostPageDto());
        return ResponseResult.ok(list);
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param sysPostCodeDto 系统岗位编码Dto
     * @return 响应结果，是否唯一
     */
    @Operation(summary = "校验岗位编码是否唯一（true：唯一，false：不唯一）")
    @GetMapping(path = "/checkCodeUnique")
    public ResponseResult<Boolean> checkCodeUnique(@Validated SysPostCodeDto sysPostCodeDto) {
        boolean checked = sysPostService.checkPostCodeUnique(sysPostCodeDto);
        return ResponseResult.ok(checked);
    }

}