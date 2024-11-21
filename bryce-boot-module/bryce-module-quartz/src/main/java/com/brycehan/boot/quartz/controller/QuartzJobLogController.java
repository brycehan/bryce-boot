package com.brycehan.boot.quartz.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.quartz.entity.convert.QuartzJobLogConvert;
import com.brycehan.boot.quartz.entity.dto.QuartzJobLogPageDto;
import com.brycehan.boot.quartz.entity.po.QuartzJobLog;
import com.brycehan.boot.quartz.entity.vo.QuartzJobLogVo;
import com.brycehan.boot.quartz.service.QuartzJobLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * quartz定时任务调度日志API
 *
 * @since 2023/10/19
 * @author Bryce Han
 */
@Tag(name = "quartz定时任务调度日志")
@RequestMapping("/quartz/jobLog")
@RestController
@RequiredArgsConstructor
public class QuartzJobLogController {

    private final QuartzJobLogService quartzJobLogService;

    /**
     * 删除quartz定时任务调度日志
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除quartz定时任务调度日志")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("hasAuthority('quartz:jobLog:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.quartzJobLogService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询quartz定时任务调度日志详情
     *
     * @param id quartz定时任务调度日志ID
     * @return 响应结果
     */
    @Operation(summary = "查询quartz定时任务调度日志详情")
    @PreAuthorize("hasAuthority('quartz:jobLog:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<QuartzJobLogVo> get(@Parameter(description = "quartz定时任务调度日志ID", required = true) @PathVariable Long id) {
        QuartzJobLog quartzJobLog = this.quartzJobLogService.getById(id);
        return ResponseResult.ok(QuartzJobLogConvert.INSTANCE.convert(quartzJobLog));
    }

    /**
     * quartz定时任务调度日志分页查询
     *
     * @param quartzJobLogPageDto 查询条件
     * @return quartz定时任务调度日志分页列表
     */
    @Operation(summary = "quartz定时任务调度日志分页查询")
    @PreAuthorize("hasAuthority('quartz:jobLog:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<QuartzJobLogVo>> page(@Validated @RequestBody QuartzJobLogPageDto quartzJobLogPageDto) {
        PageResult<QuartzJobLogVo> page = this.quartzJobLogService.page(quartzJobLogPageDto);
        return ResponseResult.ok(page);
    }

}