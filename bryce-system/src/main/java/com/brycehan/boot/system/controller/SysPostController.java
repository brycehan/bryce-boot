package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.system.convert.SysPostConvert;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.system.dto.SysPostDto;
import com.brycehan.boot.system.dto.SysPostPageDto;
import com.brycehan.boot.system.service.SysPostService;
import com.brycehan.boot.system.vo.SysPostVo;
import com.brycehan.boot.system.entity.SysPost;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 系统岗位API
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Tag(name = "sysPost", description = "系统岗位API")
@RequestMapping("/system/post")
@RestController
@AllArgsConstructor
public class SysPostController {

    private final SysPostService sysPostService;

    /**
     * 保存系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统岗位")
    @Secured("system:post:add")
    @PostMapping
    public ResponseResult<Void> add(@Parameter(description = "系统岗位", required = true)
                                    @Validated(value = SaveGroup.class) @RequestBody SysPostDto sysPostDto) {
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
    @Secured("system:post:update")
    @PatchMapping
    public ResponseResult<Void> update(@Parameter(description = "系统岗位实体", required = true) @Validated(value = UpdateGroup.class) @RequestBody SysPostDto sysPostDto) {
        this.sysPostService.update(sysPostDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统岗位
     *
     * @param idsDto 系统岗位删除Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统岗位")
    @Secured("system:post:delete")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysPostService.delete(idsDto);
        return ResponseResult.ok();
    }


    /**
     * 根据系统岗位 ID 查询系统岗位信息
     *
     * @param id 系统岗位ID
     * @return 响应结果
     */
    @Operation(summary = "根据系统岗位ID查询系统岗位详情")
    @Secured("system:post:info")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysPostVo> get(@Parameter(description = "系统岗位ID", required = true)
                                             @PathVariable String id) {
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
    @Secured("system:post:page")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysPostVo>> page(@Parameter(description = "查询信息", required = true)
        @Validated @RequestBody SysPostPageDto sysPostPageDto) {
        PageResult<SysPostVo> page = this.sysPostService.page(sysPostPageDto);
        return ResponseResult.ok(page);
    }


    /**
     * 系统岗位导出数据
     *
     * @param sysPostPageDto 查询条件
     */
    @Operation(summary = "系统岗位导出")
    @Secured("system:post:export")
    @PostMapping(path = "/export")
    public void export(@Parameter(description = "查询信息", required = true)
                       @Validated @RequestBody SysPostPageDto sysPostPageDto)  {
        this.sysPostService.export(sysPostPageDto);
    }
}

