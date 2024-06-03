package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.system.entity.convert.SysPostConvert;
import com.brycehan.boot.system.entity.dto.SysPostDto;
import com.brycehan.boot.system.entity.dto.SysPostPageDto;
import com.brycehan.boot.system.entity.po.SysPost;
import com.brycehan.boot.system.service.SysPostService;
import com.brycehan.boot.system.entity.vo.SysPostVo;
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
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('system:post:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysPostDto sysPostDto) {
        this.sysPostService.save(sysPostDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统岗位")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:post:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysPostDto sysPostDto) {
        this.sysPostService.update(sysPostDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统岗位
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统岗位")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:post:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysPostService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统岗位详情
     *
     * @param id 系统岗位ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统岗位详情")
    @PreAuthorize("hasAuthority('system:post:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysPostVo> get(@Parameter(description = "系统岗位ID", required = true) @PathVariable Long id) {
        SysPost sysPost = this.sysPostService.getById(id);
        return ResponseResult.ok(SysPostConvert.INSTANCE.convert(sysPost));
    }

    /**
     * 分页查询
     *
     * @param sysPostPageDto 查询条件
     * @return 系统岗位分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('system:post:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysPostVo>> page(@Validated @RequestBody SysPostPageDto sysPostPageDto) {
        PageResult<SysPostVo> page = this.sysPostService.page(sysPostPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统岗位导出数据
     *
     * @param sysPostPageDto 查询条件
     */
    @Operation(summary = "系统岗位导出")
    @PreAuthorize("hasAuthority('system:post:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysPostPageDto sysPostPageDto) {
        this.sysPostService.export(sysPostPageDto);
    }

    /**
     * 查询系统岗位列表
     *
     * @return 响应结果
     */
    @Operation(summary = "查询系统岗位列表")
    @GetMapping(path = "/list")
    public ResponseResult<List<SysPostVo>> list() {
        List<SysPostVo> list = this.sysPostService.list(new SysPostPageDto());
        return ResponseResult.ok(list);
    }

}