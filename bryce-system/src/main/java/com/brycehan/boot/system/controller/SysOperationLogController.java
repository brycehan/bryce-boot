package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.brycehan.boot.system.dto.SysOperationLogPageDto;
import com.brycehan.boot.system.entity.SysOperationLog;
import com.brycehan.boot.system.service.SysOperationLogService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 系统操作日志控制器
 *
 * @author Bryce Han
 * @since 2022/11/18
 */
@Tag(name = "sysOperationLog", description = "系统操作日志API")
@RequestMapping("/system/sysOperationLog")
@RestController
public class SysOperationLogController {
    
    private final SysOperationLogService sysOperationLogService;

    public SysOperationLogController(SysOperationLogService sysOperationLogService) {
        this.sysOperationLogService = sysOperationLogService;
    }

   /**
    * 保存系统操作日志
    *
    * @param sysOperationLog 系统操作日志
    * @return 响应结果
    */
    @Operation(summary = "保存系统操作日志")
    @PostMapping
    public ResponseResult<Void> save(@Parameter(description = "系统操作日志", required = true) @Validated(value = AddGroup.class) @RequestBody SysOperationLog sysOperationLog) {
        sysOperationLog.setId(IdGenerator.generate());
        this.sysOperationLogService.save(sysOperationLog);
        return ResponseResult.ok();
    }

    /**
     * 删除系统操作日志
     *
     * @param id 系统操作日志ID
     * @return 响应结果
     */
    @Operation(summary = "删除系统操作日志")
    @DeleteMapping(path = "/{id}")
    public ResponseResult<Void> deleteById(@Parameter(description = "系统操作日志ID", required = true) @PathVariable String id){
        this.sysOperationLogService.removeById(id);
        return ResponseResult.ok();
    }

   /**
    * 更新系统操作日志
    *
    * @param sysOperationLog 系统操作日志
    * @return 响应结果
    */
    @Operation(summary = "更新系统操作日志")
    @PatchMapping
    public ResponseResult<Void> update(@Parameter(description = "系统操作日志实体", required = true) @Validated(value = UpdateGroup.class) @RequestBody SysOperationLog sysOperationLog) {
        this.sysOperationLogService.updateById(sysOperationLog);
        return ResponseResult.ok();
    }

    /**
     * 根据系统操作日志 ID 查询系统操作日志信息
     *
     * @param id 系统操作日志ID
     * @return 响应结果系统操作日志详情
     */
    @Operation(summary = "根据系统操作日志ID查询系统操作日志详情")
    @GetMapping(path = "/item/{id}")
    public ResponseResult<SysOperationLog> getById(@Parameter(description = "系统操作日志ID", required = true) @PathVariable String id) {
        SysOperationLog sysOperationLog = this.sysOperationLogService.getById(id);
        return ResponseResult.ok(sysOperationLog);
    }

    /**
     * 分页查询
     *
     * @param sysOperationLogPageDto 查询条件
     * @return 响应结果分页系统操作日志
     */
    @Operation(summary = "分页查询")
    @GetMapping(path = "/page")
    public ResponseResult<PageInfo<SysOperationLog>> page(@Parameter(description = "查询信息", required = true) @RequestBody SysOperationLogPageDto sysOperationLogPageDto) {
        PageInfo<SysOperationLog> pageInfo = this.sysOperationLogService.page(sysOperationLogPageDto);
        return ResponseResult.ok(pageInfo);
    }

}

