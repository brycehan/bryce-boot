package com.brycehan.boot.quartz.controller;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.common.base.validator.SaveGroup;
import com.brycehan.boot.common.base.validator.UpdateGroup;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.quartz.common.JobStatus;
import com.brycehan.boot.quartz.entity.convert.QuartzJobConvert;
import com.brycehan.boot.quartz.entity.dto.QuartzJobDto;
import com.brycehan.boot.quartz.entity.dto.QuartzJobPageDto;
import com.brycehan.boot.quartz.entity.po.QuartzJob;
import com.brycehan.boot.quartz.entity.vo.QuartzJobVo;
import com.brycehan.boot.quartz.service.QuartzJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.quartz.CronExpression;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * quartz定时任务调度API
 *
 * @since 2023/10/17
 * @author Bryce Han
 */
@Tag(name = "quartz定时任务调度")
@RequestMapping("/quartz/job")
@RestController
@RequiredArgsConstructor
public class QuartzJobController {

    private final QuartzJobService quartzJobService;

    /**
     * 保存quartz定时任务调度
     *
     * @param quartzJobDto quartz定时任务调度Dto
     * @return 响应结果
     */
    @Operation(summary = "保存quartz定时任务调度")
    @OperateLog(type = OperatedType.INSERT)
    @PreAuthorize("@auth.hasAuthority('quartz:job:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody QuartzJobDto quartzJobDto) {
        if(!CronExpression.isValidExpression(quartzJobDto.getCronExpression())) {
            return ResponseResult.of("操作失败，Cron 表达式错误");
        }

        // 检查 Bean 的合法性
        checkBean(quartzJobDto.getBeanName());
        quartzJobService.save(quartzJobDto);

        return ResponseResult.ok();
    }

    /**
     * 更新quartz定时任务调度
     *
     * @param quartzJobDto quartz定时任务调度Dto
     * @return 响应结果
     */
    @Operation(summary = "更新quartz定时任务调度")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('quartz:job:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody QuartzJobDto quartzJobDto) {
        if(!CronExpression.isValidExpression(quartzJobDto.getCronExpression())) {
            return ResponseResult.of("操作失败，Cron 表达式错误");
        }

        // 检查 Bean 的合法性
        checkBean(quartzJobDto.getBeanName());
        quartzJobService.update(quartzJobDto);

        return ResponseResult.ok();
    }

    /**
     * 删除quartz定时任务调度
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除quartz定时任务调度")
    @OperateLog(type = OperatedType.DELETE)
    @PreAuthorize("@auth.hasAuthority('quartz:job:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        quartzJobService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询quartz定时任务调度详情
     *
     * @param id quartz定时任务调度ID
     * @return 响应结果
     */
    @Operation(summary = "查询quartz定时任务调度详情")
    @PreAuthorize("@auth.hasAuthority('quartz:job:query')")
    @GetMapping(path = "/{id}")
    public ResponseResult<QuartzJobVo> get(@Parameter(description = "quartz定时任务调度ID", required = true) @PathVariable Long id) {
        QuartzJob quartzJob = quartzJobService.getById(id);
        return ResponseResult.ok(QuartzJobConvert.INSTANCE.convert(quartzJob));
    }

    /**
     * quartz定时任务调度分页查询
     *
     * @param quartzJobPageDto 查询条件
     * @return quartz定时任务调度分页列表
     */
    @Operation(summary = "quartz定时任务调度分页查询")
    @PreAuthorize("@auth.hasAuthority('quartz:job:query')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<QuartzJobVo>> page(@Validated @RequestBody QuartzJobPageDto quartzJobPageDto) {
        PageResult<QuartzJobVo> page = quartzJobService.page(quartzJobPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * quartz定时任务调度导出数据
     *
     * @param quartzJobPageDto 查询条件
     */
    @Operation(summary = "quartz定时任务调度导出")
    @PreAuthorize("@auth.hasAuthority('quartz:job:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody QuartzJobPageDto quartzJobPageDto) {
        quartzJobService.export(quartzJobPageDto);
    }

    /**
     * 立即执行quartz定时任务
     *
     * @param quartzJobDto quartz定时任务调度Dto
     * @return 响应结果
     */
    @Operation(summary = "quartz定时任务立即执行")
    @OperateLog(type = OperatedType.OTHER)
    @PreAuthorize("@auth.hasAuthority('quartz:job:run')")
    @PutMapping(path = "/run")
    public ResponseResult<Void> run(@Validated(value = UpdateGroup.class) @RequestBody QuartzJobDto quartzJobDto) {
        quartzJobService.run(quartzJobDto);
        return ResponseResult.ok();
    }

    /**
     * 修改quartz定时任务状态
     *
     * @param id quartz定时任务调度ID
     * @param status quartz定时任务调度状态
     * @return 响应结果
     */
    @Operation(summary = "修改quartz定时任务状态")
    @OperateLog(type = OperatedType.UPDATE)
    @PreAuthorize("@auth.hasAuthority('quartz:job:update')")
    @PatchMapping(path = "/{id}/{status}")
    public ResponseResult<Void> status(@PathVariable Long id, @PathVariable JobStatus status) {
        quartzJobService.changeStatus(id, status);
        return ResponseResult.ok();
    }

    private void checkBean(String beanName) {
        // 为避免执行 jdbcTemplate 等类，只允许添加有 @Service 注解的 Bean
        String[] serviceBeans = SpringUtil.getApplicationContext().getBeanNamesForAnnotation(Service.class);
        String[] componentBeans = SpringUtil.getApplicationContext().getBeanNamesForAnnotation(Component.class);

        if(!ArrayUtil.contains(serviceBeans, beanName) && !ArrayUtil.contains(componentBeans, beanName)) {
            throw new RuntimeException("只允许添加有 @Service @Component 注解的 Bean");
        }
    }

}