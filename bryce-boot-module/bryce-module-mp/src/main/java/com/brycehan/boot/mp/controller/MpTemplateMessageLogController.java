package com.brycehan.boot.mp.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.mp.entity.convert.MpTemplateMessageLogConvert;
import com.brycehan.boot.mp.entity.dto.MpTemplateMessageLogDto;
import com.brycehan.boot.mp.entity.dto.MpTemplateMessageLogPageDto;
import com.brycehan.boot.mp.entity.po.MpTemplateMessageLog;
import com.brycehan.boot.mp.service.MpTemplateMessageLogService;
import com.brycehan.boot.mp.entity.vo.MpTemplateMessageLogVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号模版消息发送记录API
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Tag(name = "微信公众号模版消息发送记录")
@RequestMapping("/mp/templateMessageLog")
@RestController
@RequiredArgsConstructor
public class MpTemplateMessageLogController {

    private final MpTemplateMessageLogService mpTemplateMessageLogService;

    /**
     * 保存微信公众号模版消息发送记录
     *
     * @param mpTemplateMessageLogDto 微信公众号模版消息发送记录Dto
     * @return 响应结果
     */
    @Operation(summary = "保存微信公众号模版消息发送记录")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('mp:templateMessageLog:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody MpTemplateMessageLogDto mpTemplateMessageLogDto) {
        this.mpTemplateMessageLogService.save(mpTemplateMessageLogDto);
        return ResponseResult.ok();
    }

    /**
     * 更新微信公众号模版消息发送记录
     *
     * @param mpTemplateMessageLogDto 微信公众号模版消息发送记录Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信公众号模版消息发送记录")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('mp:templateMessageLog:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody MpTemplateMessageLogDto mpTemplateMessageLogDto) {
        this.mpTemplateMessageLogService.update(mpTemplateMessageLogDto);
        return ResponseResult.ok();
    }

    /**
     * 删除微信公众号模版消息发送记录
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除微信公众号模版消息发送记录")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('mp:templateMessageLog:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.mpTemplateMessageLogService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询微信公众号模版消息发送记录详情
     *
     * @param id 微信公众号模版消息发送记录ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信公众号模版消息发送记录详情")
    @PreAuthorize("hasAuthority('mp:templateMessageLog:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<MpTemplateMessageLogVo> get(@Parameter(description = "微信公众号模版消息发送记录ID", required = true) @PathVariable Long id) {
        MpTemplateMessageLog mpTemplateMessageLog = this.mpTemplateMessageLogService.getById(id);
        return ResponseResult.ok(MpTemplateMessageLogConvert.INSTANCE.convert(mpTemplateMessageLog));
    }

    /**
     * 分页查询
     *
     * @param mpTemplateMessageLogPageDto 查询条件
     * @return 微信公众号模版消息发送记录分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('mp:templateMessageLog:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<MpTemplateMessageLogVo>> page(@Validated @RequestBody MpTemplateMessageLogPageDto mpTemplateMessageLogPageDto) {
        PageResult<MpTemplateMessageLogVo> page = this.mpTemplateMessageLogService.page(mpTemplateMessageLogPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信公众号模版消息发送记录导出数据
     *
     * @param mpTemplateMessageLogPageDto 查询条件
     */
    @Operation(summary = "微信公众号模版消息发送记录导出")
    @PreAuthorize("hasAuthority('mp:templateMessageLog:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody MpTemplateMessageLogPageDto mpTemplateMessageLogPageDto) {
        this.mpTemplateMessageLogService.export(mpTemplateMessageLogPageDto);
    }

}