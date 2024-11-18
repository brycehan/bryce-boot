package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.dto.SysNoticeDto;
import com.brycehan.boot.system.entity.dto.SysNoticePageDto;
import com.brycehan.boot.system.entity.vo.SysNoticeVo;
import com.brycehan.boot.system.service.SysNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统通知公告API
 *
 * @since 2023/10/13
 * @author Bryce Han
 */
@Tag(name = "系统通知公告")
@RequestMapping("/system/notice")
@RestController
@RequiredArgsConstructor
public class SysNoticeController {

    private final SysNoticeService sysNoticeService;

    /**
     * 保存系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统通知公告")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("hasAuthority('system:notice:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysNoticeDto sysNoticeDto) {
        this.sysNoticeService.save(sysNoticeDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统通知公告
     *
     * @param sysNoticeDto 系统通知公告Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统通知公告")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("hasAuthority('system:notice:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysNoticeDto sysNoticeDto) {
        this.sysNoticeService.update(sysNoticeDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统通知公告
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统通知公告")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("hasAuthority('system:notice:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysNoticeService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统通知公告详情
     *
     * @param id 系统通知公告ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统通知公告详情")
    @PreAuthorize("hasAuthority('system:notice:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysNoticeVo> get(@Parameter(description = "系统通知公告ID", required = true) @PathVariable Long id) {
        SysNoticeVo sysNoticeVo = this.sysNoticeService.get(id);
        return ResponseResult.ok(sysNoticeVo);
    }

    /**
     * 系统通知公告分页查询
     *
     * @param sysNoticePageDto 查询条件
     * @return 系统通知公告分页列表
     */
    @Operation(summary = "系统通知公告分页查询")
    @PreAuthorize("hasAuthority('system:notice:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysNoticeVo>> page(@Validated @RequestBody SysNoticePageDto sysNoticePageDto) {
        PageResult<SysNoticeVo> page = this.sysNoticeService.page(sysNoticePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统通知公告导出数据
     *
     * @param sysNoticePageDto 查询条件
     */
    @Operation(summary = "系统通知公告导出")
    @PreAuthorize("hasAuthority('system:notice:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysNoticePageDto sysNoticePageDto) {
        this.sysNoticeService.export(sysNoticePageDto);
    }

}