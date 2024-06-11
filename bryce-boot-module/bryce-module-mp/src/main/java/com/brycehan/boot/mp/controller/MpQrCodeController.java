package com.brycehan.boot.mp.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.mp.entity.convert.MpQrCodeConvert;
import com.brycehan.boot.mp.entity.dto.MpQrCodeDto;
import com.brycehan.boot.mp.entity.dto.MpQrCodePageDto;
import com.brycehan.boot.mp.entity.po.MpQrCode;
import com.brycehan.boot.mp.entity.vo.MpQrCodeVo;
import com.brycehan.boot.mp.service.MpQrCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 微信公众号带参二维码API
 *
 * @author Bryce Han
 * @since 2024/03/28
 */
@Tag(name = "微信公众号带参二维码")
@RequestMapping("/mp/qrCode")
@RestController
@RequiredArgsConstructor
public class MpQrCodeController {

    private final MpQrCodeService mpQrCodeService;

    /**
     * 保存微信公众号带参二维码
     *
     * @param mpQrCodeDto 微信公众号带参二维码Dto
     * @return 响应结果
     */
    @Operation(summary = "保存微信公众号带参二维码")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('mp:qrCode:save')")
    @PostMapping
    public ResponseResult<WxMpQrCodeTicket> save(@Validated(value = SaveGroup.class) @RequestBody MpQrCodeDto mpQrCodeDto) {
        WxMpQrCodeTicket ticket = this.mpQrCodeService.save(mpQrCodeDto);
        return ResponseResult.ok(ticket);
    }

    /**
     * 更新微信公众号带参二维码
     *
     * @param mpQrCodeDto 微信公众号带参二维码Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信公众号带参二维码")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('mp:qrCode:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody MpQrCodeDto mpQrCodeDto) {
        this.mpQrCodeService.update(mpQrCodeDto);
        return ResponseResult.ok();
    }

    /**
     * 删除微信公众号带参二维码
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除微信公众号带参二维码")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('mp:qrCode:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.mpQrCodeService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询微信公众号带参二维码详情
     *
     * @param id 微信公众号带参二维码ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信公众号带参二维码详情")
    @PreAuthorize("hasAuthority('mp:qrCode:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<MpQrCodeVo> get(@Parameter(description = "微信公众号带参二维码ID", required = true) @PathVariable Long id) {
        MpQrCode mpQrCode = this.mpQrCodeService.getById(id);
        return ResponseResult.ok(MpQrCodeConvert.INSTANCE.convert(mpQrCode));
    }

    /**
     * 分页查询
     *
     * @param mpQrCodePageDto 查询条件
     * @return 微信公众号带参二维码分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('mp:qrCode:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<MpQrCodeVo>> page(@Validated @RequestBody MpQrCodePageDto mpQrCodePageDto) {
        PageResult<MpQrCodeVo> page = this.mpQrCodeService.page(mpQrCodePageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信公众号带参二维码导出数据
     *
     * @param mpQrCodePageDto 查询条件
     */
    @Operation(summary = "微信公众号带参二维码导出")
    @PreAuthorize("hasAuthority('mp:qrCode:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody MpQrCodePageDto mpQrCodePageDto) {
        this.mpQrCodeService.export(mpQrCodePageDto);
    }

}