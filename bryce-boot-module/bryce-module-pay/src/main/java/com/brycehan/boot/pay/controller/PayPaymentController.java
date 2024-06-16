package com.brycehan.boot.pay.controller;

import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.pay.entity.convert.PayPaymentConvert;
import com.brycehan.boot.pay.entity.dto.PayPaymentDto;
import com.brycehan.boot.pay.entity.dto.PayPaymentPageDto;
import com.brycehan.boot.pay.entity.po.PayPayment;
import com.brycehan.boot.pay.entity.vo.PayPaymentVo;
import com.brycehan.boot.pay.service.PayPaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 支付记录API
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Tag(name = "支付记录")
@RequestMapping("/pay/payment")
@RestController
@RequiredArgsConstructor
public class PayPaymentController {

    private final PayPaymentService payPaymentService;

    /**
     * 保存支付记录
     *
     * @param payPaymentDto 支付记录Dto
     * @return 响应结果
     */
    @Operation(summary = "保存支付记录")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('pay:payment:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody PayPaymentDto payPaymentDto) {
        this.payPaymentService.save(payPaymentDto);
        return ResponseResult.ok();
    }

    /**
     * 更新支付记录
     *
     * @param payPaymentDto 支付记录Dto
     * @return 响应结果
     */
    @Operation(summary = "更新支付记录")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('pay:payment:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody PayPaymentDto payPaymentDto) {
        this.payPaymentService.update(payPaymentDto);
        return ResponseResult.ok();
    }

    /**
     * 删除支付记录
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除支付记录")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('pay:payment:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.payPaymentService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询支付记录详情
     *
     * @param id 支付记录ID
     * @return 响应结果
     */
    @Operation(summary = "查询支付记录详情")
    @PreAuthorize("hasAuthority('pay:payment:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<PayPaymentVo> get(@Parameter(description = "支付记录ID", required = true) @PathVariable Long id) {
        PayPayment payPayment = this.payPaymentService.getById(id);
        return ResponseResult.ok(PayPaymentConvert.INSTANCE.convert(payPayment));
    }

    /**
     * 支付记录分页查询
     *
     * @param payPaymentPageDto 查询条件
     * @return 支付记录分页列表
     */
    @Operation(summary = "支付记录分页查询")
    @PreAuthorize("hasAuthority('pay:payment:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<PayPaymentVo>> page(@Validated @RequestBody PayPaymentPageDto payPaymentPageDto) {
        PageResult<PayPaymentVo> page = this.payPaymentService.page(payPaymentPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 支付记录导出数据
     *
     * @param payPaymentPageDto 查询条件
     */
    @Operation(summary = "支付记录导出")
    @PreAuthorize("hasAuthority('pay:payment:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody PayPaymentPageDto payPaymentPageDto) {
        this.payPaymentService.export(payPaymentPageDto);
    }

}