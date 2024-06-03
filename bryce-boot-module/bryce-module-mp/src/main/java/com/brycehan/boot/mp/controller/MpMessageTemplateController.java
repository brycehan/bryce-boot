package com.brycehan.boot.mp.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.mp.entity.convert.MpMessageTemplateConvert;
import com.brycehan.boot.mp.entity.dto.MpMessageTemplateDto;
import com.brycehan.boot.mp.entity.dto.MpMessageTemplatePageDto;
import com.brycehan.boot.mp.entity.dto.MpTemplateMessageBatchDto;
import com.brycehan.boot.mp.entity.po.MpMessageTemplate;
import com.brycehan.boot.mp.service.MpMessageTemplateService;
import com.brycehan.boot.mp.service.MpTemplateMessageService;
import com.brycehan.boot.mp.entity.vo.MpMessageTemplateVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号消息模板API
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Tag(name = "微信公众号消息模板")
@RequestMapping("/mp/messageTemplate")
@RestController
@RequiredArgsConstructor
public class MpMessageTemplateController {

    private final MpMessageTemplateService mpMessageTemplateService;
    private final MpTemplateMessageService mpTemplateMessageService;

    /**
     * 保存微信公众号消息模板
     *
     * @param mpMessageTemplateDto 微信公众号消息模板Dto
     * @return 响应结果
     */
    @Operation(summary = "保存微信公众号消息模板")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('mp:messageTemplate:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody MpMessageTemplateDto mpMessageTemplateDto) {
        this.mpMessageTemplateService.save(mpMessageTemplateDto);
        return ResponseResult.ok();
    }

    /**
     * 更新微信公众号消息模板
     *
     * @param mpMessageTemplateDto 微信公众号消息模板Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信公众号消息模板")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('mp:messageTemplate:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody MpMessageTemplateDto mpMessageTemplateDto) {
        this.mpMessageTemplateService.update(mpMessageTemplateDto);
        return ResponseResult.ok();
    }

    /**
     * 删除微信公众号消息模板
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除微信公众号消息模板")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('mp:messageTemplate:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.mpMessageTemplateService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询微信公众号消息模板详情
     *
     * @param id 微信公众号消息模板ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信公众号消息模板详情")
    @PreAuthorize("hasAuthority('mp:messageTemplate:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<MpMessageTemplateVo> get(@Parameter(description = "微信公众号消息模板ID", required = true) @PathVariable Long id) {
        MpMessageTemplate mpMessageTemplate = this.mpMessageTemplateService.getById(id);
        return ResponseResult.ok(MpMessageTemplateConvert.INSTANCE.convert(mpMessageTemplate));
    }

    /**
     * 分页查询
     *
     * @param mpMessageTemplatePageDto 查询条件
     * @return 微信公众号消息模板分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('mp:messageTemplate:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<MpMessageTemplateVo>> page(@Validated @RequestBody MpMessageTemplatePageDto mpMessageTemplatePageDto) {
        PageResult<MpMessageTemplateVo> page = this.mpMessageTemplateService.page(mpMessageTemplatePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信公众号消息模板导出数据
     *
     * @param mpMessageTemplatePageDto 查询条件
     */
    @Operation(summary = "微信公众号消息模板导出")
    @PreAuthorize("hasAuthority('mp:messageTemplate:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody MpMessageTemplatePageDto mpMessageTemplatePageDto) {
        this.mpMessageTemplateService.export(mpMessageTemplatePageDto);
    }

    /**
     * 通过名称查询消息模板详情
     *
     * @param name 消息模板名称
     * @return 消息模板详情
     */
    @Operation(summary = "通过名称查询消息模板详情")
    @PreAuthorize("hasAuthority('mp:messageTemplate:info')")
    @GetMapping(path = "/getByName")
    public ResponseResult<MpMessageTemplate> getByName(@RequestParam String name) {
        LambdaQueryWrapper<MpMessageTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MpMessageTemplate::getName, name);

        MpMessageTemplate messageTemplate = this.mpMessageTemplateService.getOne(queryWrapper);
        return ResponseResult.ok(messageTemplate);
    }

    /**
     * 同步公众号消息模板
     *
     * @return 响应结果
     */
    @Operation(summary = "同步公众号消息模板")
    @PreAuthorize("hasAuthority('mp:messageTemplate:info')")
    @GetMapping(path = "/syncMpTemplate")
    public ResponseResult<MpMessageTemplate> syncMpTemplate() {
        this.mpMessageTemplateService.syncMpTemplate();
        return ResponseResult.ok();
    }

    /**
     * 批量向用户发送模板消息，通过用户筛选条件（一般使用标签筛选），将消息发送给数据库中所有符合条件的用户
     *
     * @param templateMessageBatchDto 模板消息内容
     * @return 响应结果
     */
    @Operation(summary = "通过名称查询消息模板详情")
    @PreAuthorize("hasAuthority('mp:messageTemplate:save')")
    @GetMapping(path = "/sendMessageBatch")
    public ResponseResult<MpMessageTemplate> sendMessageBatch(@RequestBody MpTemplateMessageBatchDto templateMessageBatchDto) {
        this.mpTemplateMessageService.sendMessageBatch(templateMessageBatchDto);
        return ResponseResult.ok();
    }

}