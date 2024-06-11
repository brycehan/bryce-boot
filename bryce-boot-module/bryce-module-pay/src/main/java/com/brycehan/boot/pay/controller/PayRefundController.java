package com.brycehan.boot.pay.controller;

import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.pay.entity.convert.PayRefundConvert;
import com.brycehan.boot.pay.entity.dto.PayRefundDto;
import com.brycehan.boot.pay.entity.dto.PayRefundPageDto;
import com.brycehan.boot.pay.entity.po.PayRefund;
import com.brycehan.boot.pay.entity.vo.PayRefundVo;
import com.brycehan.boot.pay.service.PayRefundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 退款单API
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Tag(name = "退款单")
@RequestMapping("/pay/refund")
@RestController
@RequiredArgsConstructor
public class PayRefundController {

    private final PayRefundService payRefundService;

    /**
     * 保存退款单
     *
     * @param payRefundDto 退款单Dto
     * @return 响应结果
     */
    @Operation(summary = "保存退款单")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('pay:refund:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody PayRefundDto payRefundDto) {
        this.payRefundService.save(payRefundDto);
        return ResponseResult.ok();
    }

    /**
     * 更新退款单
     *
     * @param payRefundDto 退款单Dto
     * @return 响应结果
     */
    @Operation(summary = "更新退款单")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('pay:refund:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody PayRefundDto payRefundDto) {
        this.payRefundService.update(payRefundDto);
        return ResponseResult.ok();
    }

    /**
     * 删除退款单
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除退款单")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('pay:refund:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.payRefundService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询退款单详情
     *
     * @param id 退款单ID
     * @return 响应结果
     */
    @Operation(summary = "查询退款单详情")
    @PreAuthorize("hasAuthority('pay:refund:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<PayRefundVo> get(@Parameter(description = "退款单ID", required = true) @PathVariable Long id) {
        PayRefund payRefund = this.payRefundService.getById(id);
        return ResponseResult.ok(PayRefundConvert.INSTANCE.convert(payRefund));
    }

    /**
     * 退款单分页查询
     *
     * @param payRefundPageDto 查询条件
     * @return 退款单分页列表
     */
    @Operation(summary = "退款单分页查询")
    @PreAuthorize("hasAuthority('pay:refund:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<PayRefundVo>> page(@Validated @RequestBody PayRefundPageDto payRefundPageDto) {
        PageResult<PayRefundVo> page = this.payRefundService.page(payRefundPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 退款单导出数据
     *
     * @param payRefundPageDto 查询条件
     */
    @Operation(summary = "退款单导出")
    @PreAuthorize("hasAuthority('pay:refund:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody PayRefundPageDto payRefundPageDto) {
        this.payRefundService.export(payRefundPageDto);
    }

}