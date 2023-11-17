package com.brycehan.boot.quartz.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.quartz.convert.QuartzJobConvert;
import com.brycehan.boot.quartz.dto.QuartzJobDto;
import com.brycehan.boot.quartz.dto.QuartzJobPageDto;
import com.brycehan.boot.quartz.entity.QuartzJob;
import com.brycehan.boot.quartz.service.QuartzJobService;
import com.brycehan.boot.quartz.vo.QuartzJobVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.quartz.CronExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * quartz 定时任务调度API
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Tag(name = "quartz 定时任务调度", description = "quartzJob")
@RequestMapping("/quartz/job")
@RestController
@RequiredArgsConstructor
public class QuartzJobController {

    private final QuartzJobService quartzJobService;

    /**
     * 保存quartz 定时任务调度
     *
     * @param quartzJobDto quartz 定时任务调度Dto
     * @return 响应结果
     */
    @Operation(summary = "保存quartz 定时任务调度")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('quartz:job:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody QuartzJobDto quartzJobDto) {
        if(!CronExpression.isValidExpression(quartzJobDto.getCronExpression())) {
            return ResponseResult.error("操作失败，Cron 表达式错误");
        }

        // 检查 Bean 的合法性
        checkBean(quartzJobDto.getBeanName());

        this.quartzJobService.save(quartzJobDto);

        return ResponseResult.ok();
    }

    /**
     * 更新quartz 定时任务调度
     *
     * @param quartzJobDto quartz 定时任务调度Dto
     * @return 响应结果
     */
    @Operation(summary = "更新quartz 定时任务调度")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('quartz:job:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody QuartzJobDto quartzJobDto) {
        if(!CronExpression.isValidExpression(quartzJobDto.getCronExpression())) {
            return ResponseResult.error("操作失败，Cron 表达式错误");
        }

        // 检查 Bean 的合法性
        checkBean(quartzJobDto.getBeanName());

        this.quartzJobService.update(quartzJobDto);
        return ResponseResult.ok();
    }

    /**
     * 删除quartz 定时任务调度
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除quartz 定时任务调度")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('quartz:job:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.quartzJobService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询quartz 定时任务调度详情
     *
     * @param id quartz 定时任务调度ID
     * @return 响应结果
     */
    @Operation(summary = "查询quartz 定时任务调度详情")
    @PreAuthorize("hasAuthority('quartz:job:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<QuartzJobVo> get(@Parameter(description = "quartz 定时任务调度ID", required = true) @PathVariable Long id) {
        QuartzJob quartzJob = this.quartzJobService.getById(id);
        return ResponseResult.ok(QuartzJobConvert.INSTANCE.convert(quartzJob));
    }

    /**
     * 分页查询
     *
     * @param quartzJobPageDto 查询条件
     * @return quartz 定时任务调度分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('quartz:job:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<QuartzJobVo>> page(@Validated @RequestBody QuartzJobPageDto quartzJobPageDto) {
        PageResult<QuartzJobVo> page = this.quartzJobService.page(quartzJobPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * quartz 定时任务调度导出数据
     *
     * @param quartzJobPageDto 查询条件
     */
    @Operation(summary = "quartz 定时任务调度导出")
    @PreAuthorize("hasAuthority('quartz:job:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody QuartzJobPageDto quartzJobPageDto) {
        this.quartzJobService.export(quartzJobPageDto);
    }

    /**
     * 立即执行
     *
     * @param quartzJobDto quartz 定时任务调度Dto
     * @return 响应结果
     */
    @Operation(summary = "quartz 定时任务立即执行")
    @OperateLog(type = OperateType.OTHER)
    @PreAuthorize("hasAuthority('quartz:job:run')")
    @PutMapping(path = "/run")
    public ResponseResult<Void> run(@Validated(value = UpdateGroup.class) @RequestBody QuartzJobDto quartzJobDto) {

        this.quartzJobService.run(quartzJobDto);
        return ResponseResult.ok();
    }

    /**
     * 修改 quartz 定时任务状态
     *
     * @param quartzJobDto quartz 定时任务调度Dto
     * @return 响应结果
     */
    @Operation(summary = "修改 quartz 定时任务状态")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('quartz:job:update')")
    @PutMapping(path = "/status")
    public ResponseResult<Void> status(@Validated(value = UpdateGroup.class) @RequestBody QuartzJobDto quartzJobDto) {

        this.quartzJobService.changeStatus(quartzJobDto);
        return ResponseResult.ok();
    }


    private void checkBean(String beanName) {
        // 为避免执行 jdbcTemplate 等类，只允许添加有 @Service 注解的 Bean
        String[] serviceBeans = SpringUtil.getApplicationContext().getBeanNamesForAnnotation(Service.class);

        if(!ArrayUtil.contains(serviceBeans, beanName)) {
            throw new RuntimeException("只允许添加有 @Service 注解的 Bean");
        }
    }
}