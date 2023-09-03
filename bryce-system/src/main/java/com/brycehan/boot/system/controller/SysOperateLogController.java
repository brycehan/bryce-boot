package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.system.convert.SysOperateLogConvert;
import com.brycehan.boot.system.dto.SysOperateLogPageDto;
import com.brycehan.boot.system.entity.SysOperateLog;
import com.brycehan.boot.system.service.SysOperateLogService;
import com.brycehan.boot.system.vo.SysOperateLogVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 系统操作日志API
 *
 * @author Bryce Han
 * @since 2023/08/30
 */
@Tag(name = "sysOperateLog", description = "系统操作日志API")
@RequestMapping("/system/operateLog")
@RestController
@RequiredArgsConstructor
public class SysOperateLogController {

    private final SysOperateLogService sysOperateLogService;

    /**
     * 查询系统操作日志信息
     *
     * @param id 系统操作日志ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统操作日志详情")
    @Secured("system:operateLog:info")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysOperateLogVo> get(@Parameter(description = "系统操作日志ID", required = true)
                                               @PathVariable String id) {
        SysOperateLog sysOperateLog = this.sysOperateLogService.getById(id);
        return ResponseResult.ok(SysOperateLogConvert.INSTANCE.convert(sysOperateLog));
    }

    /**
     * 分页查询
     *
     * @param sysOperateLogPageDto 查询条件
     * @return 系统操作日志分页列表
     */
    @Operation(summary = "分页查询")
    @Secured("system:operateLog:page")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysOperateLogVo>> page(@Parameter(description = "查询信息", required = true)
                                                            @Validated @RequestBody SysOperateLogPageDto sysOperateLogPageDto) {
        PageResult<SysOperateLogVo> page = this.sysOperateLogService.page(sysOperateLogPageDto);
        return ResponseResult.ok(page);
    }

}