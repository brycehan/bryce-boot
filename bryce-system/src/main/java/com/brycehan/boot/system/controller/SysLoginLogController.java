package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.system.convert.SysLoginLogConvert;
import com.brycehan.boot.system.dto.SysLoginLogPageDto;
import com.brycehan.boot.system.entity.SysLoginLog;
import com.brycehan.boot.system.service.SysLoginLogService;
import com.brycehan.boot.system.vo.SysLoginLogVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统登录日志API
 *
 * @since 2023/09/25
 * @author Bryce Han
 */
@Tag(name = "系统登录日志", description = "sysLoginLog")
@RequestMapping("/system/loginLog")
@RestController
@RequiredArgsConstructor
public class SysLoginLogController {

    private final SysLoginLogService sysLoginLogService;

    /**
     * 删除系统登录日志
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统登录日志")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:loginLog:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.sysLoginLogService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询系统登录日志详情
     *
     * @param id 系统登录日志ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统登录日志详情")
    @PreAuthorize("hasAuthority('system:loginLog:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysLoginLogVo> get(@Parameter(description = "系统登录日志ID", required = true) @PathVariable String id) {
        SysLoginLog sysLoginLog = this.sysLoginLogService.getById(id);
        return ResponseResult.ok(SysLoginLogConvert.INSTANCE.convert(sysLoginLog));
    }

    /**
     * 分页查询
     *
     * @param sysLoginLogPageDto 查询条件
     * @return 系统登录日志分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('system:loginLog:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysLoginLogVo>> page(@Validated @RequestBody SysLoginLogPageDto sysLoginLogPageDto) {
        PageResult<SysLoginLogVo> page = this.sysLoginLogService.page(sysLoginLogPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统登录日志导出数据
     *
     * @param sysLoginLogPageDto 查询条件
     */
    @Operation(summary = "系统登录日志导出")
    @PreAuthorize("hasAuthority('system:loginLog:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysLoginLogPageDto sysLoginLogPageDto) {
        this.sysLoginLogService.export(sysLoginLogPageDto);
    }

}