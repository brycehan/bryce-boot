package com.brycehan.boot.pay.controller;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.pay.convert.PayOrderConvert;
import com.brycehan.boot.pay.dto.PayOrderPageDto;
import com.brycehan.boot.pay.entity.PayOrder;
import com.brycehan.boot.pay.enums.OrderStatus;
import com.brycehan.boot.pay.service.PayOrderService;
import com.brycehan.boot.pay.vo.PayOrderVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 订单API
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Tag(name = "订单")
@RequestMapping("/pay/order")
@RestController
@RequiredArgsConstructor
public class PayOrderController {

    private final PayOrderService payOrderService;

    /**
     * 删除订单
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除订单")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('pay:order:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.payOrderService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询订单详情
     *
     * @param id 订单ID
     * @return 响应结果
     */
    @Operation(summary = "查询订单详情")
    @PreAuthorize("hasAuthority('pay:order:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<PayOrderVo> get(@Parameter(description = "订单ID", required = true) @PathVariable Long id) {
        PayOrder payOrder = this.payOrderService.getById(id);
        return ResponseResult.ok(PayOrderConvert.INSTANCE.convert(payOrder));
    }

    /**
     * 分页查询
     *
     * @param payOrderPageDto 查询条件
     * @return 订单分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('pay:order:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<PayOrderVo>> page(@Validated @RequestBody PayOrderPageDto payOrderPageDto) {
        PageResult<PayOrderVo> page = this.payOrderService.page(payOrderPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 订单导出数据
     *
     * @param payOrderPageDto 查询条件
     */
    @Operation(summary = "订单导出")
    @PreAuthorize("hasAuthority('pay:order:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody PayOrderPageDto payOrderPageDto) {
        this.payOrderService.export(payOrderPageDto);
    }

    /**
     * 用户订单分页查询
     *
     * @param payOrderPageDto 查询条件
     * @return 订单分页列表
     */
    @Operation(summary = "用户订单分页查询")
    @PreAuthorize("hasAuthority('pay:order:userPage')")
    @PostMapping(path = "/page/user")
    public ResponseResult<PageResult<PayOrderVo>> userPage(@Validated @RequestBody PayOrderPageDto payOrderPageDto) {
        PageResult<PayOrderVo> page = this.payOrderService.userPage(payOrderPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 查询本地订单状态
     *
     * @param orderNo 订单号
     * @return 订单状态
     */
    @Operation(summary = "查询本地订单状态")
    @PostMapping(path = "/queryOrderStatus/{orderNo}")
    public ResponseResult<Object> queryOrderStatus(@PathVariable String orderNo) {
        String orderStatus = this.payOrderService.getOrderStatus(orderNo);

        if(OrderStatus.SUCCESS.getType().equals(orderStatus)){
            return ResponseResult.ok().setMessage("支付成功");
        }

        // 支付成功外的其它状态
        return ResponseResult.ok()
                .setCode(601)
                .setMessage(orderStatus);
    }

}