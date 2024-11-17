package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.system.entity.dto.SysOperateLogPageDto;
import com.brycehan.boot.system.entity.vo.SysOperateLogVo;
import com.brycehan.boot.system.service.SysOperateLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统操作日志API
 *
 * @since 2023/09/27
 * @author Bryce Han
 */
@Tag(name = "系统操作日志")
@RequestMapping("/system/operateLog")
@RestController
@RequiredArgsConstructor
public class SysOperateLogController {

    private final SysOperateLogService sysOperateLogService;

    /**
     * 删除系统操作日志
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统操作日志")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:operateLog:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysOperateLogService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统操作日志详情
     *
     * @param id 系统操作日志ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统操作日志详情")
    @PreAuthorize("hasAuthority('system:operateLog:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysOperateLogVo> get(@Parameter(description = "系统操作日志ID", required = true) @PathVariable Long id) {
        SysOperateLogVo sysOperateLogVo = this.sysOperateLogService.get(id);
        return ResponseResult.ok(sysOperateLogVo);
    }

    /**
     * 系统操作日志分页查询
     *
     * @param sysOperateLogPageDto 查询条件
     * @return 系统操作日志分页列表
     */
    @Operation(summary = "系统操作日志分页查询")
    @PreAuthorize("hasAuthority('system:operateLog:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysOperateLogVo>> page(@Validated @RequestBody SysOperateLogPageDto sysOperateLogPageDto) {
        PageResult<SysOperateLogVo> page = this.sysOperateLogService.page(sysOperateLogPageDto);
        return ResponseResult.ok(page);
    }

}