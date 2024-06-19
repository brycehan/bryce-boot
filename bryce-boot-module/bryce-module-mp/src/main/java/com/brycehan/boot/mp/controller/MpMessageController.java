package com.brycehan.boot.mp.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.mp.entity.convert.MpMessageConvert;
import com.brycehan.boot.mp.entity.dto.MpMessageDto;
import com.brycehan.boot.mp.entity.dto.MpMessagePageDto;
import com.brycehan.boot.mp.entity.dto.MpMessageReplyDto;
import com.brycehan.boot.mp.entity.po.MpMessage;
import com.brycehan.boot.mp.entity.vo.MpMessageVo;
import com.brycehan.boot.mp.service.MpMessageReplyService;
import com.brycehan.boot.mp.service.MpMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号消息API
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Tag(name = "微信公众号消息")
@RequestMapping("/mp/message")
@RestController
@RequiredArgsConstructor
public class MpMessageController {

    private final MpMessageService mpMessageService;
    private final MpMessageReplyService mpMessageReplyService;

    /**
     * 保存微信公众号消息
     *
     * @param mpMessageDto 微信公众号消息Dto
     * @return 响应结果
     */
    @Operation(summary = "保存微信公众号消息")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('mp:message:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody MpMessageDto mpMessageDto) {
        this.mpMessageService.save(mpMessageDto);
        return ResponseResult.ok();
    }

    /**
     * 更新微信公众号消息
     *
     * @param mpMessageDto 微信公众号消息Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信公众号消息")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('mp:message:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody MpMessageDto mpMessageDto) {
        this.mpMessageService.update(mpMessageDto);
        return ResponseResult.ok();
    }

    /**
     * 删除微信公众号消息
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除微信公众号消息")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('mp:message:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.mpMessageService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询微信公众号消息详情
     *
     * @param id 微信公众号消息ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信公众号消息详情")
    @PreAuthorize("hasAuthority('mp:message:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<MpMessageVo> get(@Parameter(description = "微信公众号消息ID", required = true) @PathVariable Long id) {
        MpMessage mpMessage = this.mpMessageService.getById(id);
        return ResponseResult.ok(MpMessageConvert.INSTANCE.convert(mpMessage));
    }

    /**
     * 微信公众号消息分页
     *
     * @param mpMessagePageDto 查询条件
     * @return 微信公众号消息分页列表
     */
    @Operation(summary = "微信公众号消息分页")
    @PreAuthorize("hasAuthority('mp:message:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<MpMessageVo>> page(@Validated @RequestBody MpMessagePageDto mpMessagePageDto) {
        PageResult<MpMessageVo> page = this.mpMessageService.page(mpMessagePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信公众号消息导出数据
     *
     * @param mpMessagePageDto 查询条件
     */
    @Operation(summary = "微信公众号消息导出")
    @PreAuthorize("hasAuthority('mp:message:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody MpMessagePageDto mpMessagePageDto) {
        this.mpMessageService.export(mpMessagePageDto);
    }

    /**
     * 微信公众号回复消息
     *
     * @param replyDto 查询条件
     * @return 响应结果
     */
    @Operation(summary = "微信公众号回复消息")
    @PreAuthorize("hasAuthority('mp:message:page')")
    @PostMapping(path = "/reply")
    public ResponseResult<Void> reply(@Validated @RequestBody MpMessageReplyDto replyDto) {
        this.mpMessageReplyService.reply(replyDto.getOpenId(), replyDto.getReplyType(), replyDto.getContent());
        return ResponseResult.ok();
    }

}