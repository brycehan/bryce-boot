package com.brycehan.boot.mp.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.mp.entity.convert.MpMessageReplyRuleConvert;
import com.brycehan.boot.mp.entity.dto.MpMessageReplyRuleDto;
import com.brycehan.boot.mp.entity.dto.MpMessageReplyRulePageDto;
import com.brycehan.boot.mp.entity.po.MpMessageReplyRule;
import com.brycehan.boot.mp.service.MpMessageReplyRuleService;
import com.brycehan.boot.mp.entity.vo.MpMessageReplyRuleVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号消息回复规则API
 *
 * @author Bryce Han
 * @since 2024/03/26
 */
@Tag(name = "微信公众号自动回复规则")
@RequestMapping("/mp/messageReplyRule")
@RestController
@RequiredArgsConstructor
public class MpMessageReplyRuleController {

    private final MpMessageReplyRuleService mpMessageReplyRuleService;

    /**
     * 保存微信公众号消息回复规则
     *
     * @param mpMessageReplyRuleDto 微信公众号消息回复规则Dto
     * @return 响应结果
     */
    @Operation(summary = "保存微信公众号消息回复规则")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('mp:messageReplyRule:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody MpMessageReplyRuleDto mpMessageReplyRuleDto) {
        this.mpMessageReplyRuleService.save(mpMessageReplyRuleDto);
        return ResponseResult.ok();
    }

    /**
     * 更新微信公众号消息回复规则
     *
     * @param mpMessageReplyRuleDto 微信公众号消息回复规则Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信公众号消息回复规则")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('mp:messageReplyRule:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody MpMessageReplyRuleDto mpMessageReplyRuleDto) {
        this.mpMessageReplyRuleService.update(mpMessageReplyRuleDto);
        return ResponseResult.ok();
    }

    /**
     * 删除微信公众号消息回复规则
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除微信公众号消息回复规则")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('mp:messageReplyRule:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.mpMessageReplyRuleService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询微信公众号消息回复规则详情
     *
     * @param id 微信公众号消息回复规则ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信公众号消息回复规则详情")
    @PreAuthorize("hasAuthority('mp:messageReplyRule:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<MpMessageReplyRuleVo> get(@Parameter(description = "微信公众号消息回复规则ID", required = true) @PathVariable Long id) {
        MpMessageReplyRule mpMessageReplyRule = this.mpMessageReplyRuleService.getById(id);
        return ResponseResult.ok(MpMessageReplyRuleConvert.INSTANCE.convert(mpMessageReplyRule));
    }

    /**
     * 分页查询
     *
     * @param mpMessageReplyRulePageDto 查询条件
     * @return 微信公众号消息回复规则分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('mp:messageReplyRule:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<MpMessageReplyRuleVo>> page(@Validated @RequestBody MpMessageReplyRulePageDto mpMessageReplyRulePageDto) {
        PageResult<MpMessageReplyRuleVo> page = this.mpMessageReplyRuleService.page(mpMessageReplyRulePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信公众号消息回复规则导出数据
     *
     * @param mpMessageReplyRulePageDto 查询条件
     */
    @Operation(summary = "微信公众号消息回复规则导出")
    @PreAuthorize("hasAuthority('mp:messageReplyRule:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody MpMessageReplyRulePageDto mpMessageReplyRulePageDto) {
        this.mpMessageReplyRuleService.export(mpMessageReplyRulePageDto);
    }

}