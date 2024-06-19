package com.brycehan.boot.pay.controller;

import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.pay.entity.convert.PayProductConvert;
import com.brycehan.boot.pay.entity.dto.PayProductDto;
import com.brycehan.boot.pay.entity.dto.PayProductPageDto;
import com.brycehan.boot.pay.entity.po.PayProduct;
import com.brycehan.boot.pay.entity.vo.PayProductVo;
import com.brycehan.boot.pay.service.PayProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品API
 *
 * @author Bryce Han
 * @since 2024/02/28
 */
@Tag(name = "商品")
@RequestMapping("/pay/product")
@RestController
@RequiredArgsConstructor
public class PayProductController {

    private final PayProductService payProductService;

    /**
     * 保存商品
     *
     * @param payProductDto 商品Dto
     * @return 响应结果
     */
    @Operation(summary = "保存商品")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('pay:product:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody PayProductDto payProductDto) {
        this.payProductService.save(payProductDto);
        return ResponseResult.ok();
    }

    /**
     * 更新商品
     *
     * @param payProductDto 商品Dto
     * @return 响应结果
     */
    @Operation(summary = "更新商品")
    @OperateLog(type = OperateType.UPDATE)
//    @PreAuthorize("hasAuthority('pay:product:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody PayProductDto payProductDto) {
        this.payProductService.update(payProductDto);
        return ResponseResult.ok();
    }

    /**
     * 删除商品
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除商品")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('pay:product:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        this.payProductService.delete(idsDto);
        return ResponseResult.ok();
    }

    /**
     * 查询商品详情
     *
     * @param id 商品ID
     * @return 响应结果
     */
    @Operation(summary = "查询商品详情")
    @PreAuthorize("hasAuthority('pay:product:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<PayProductVo> get(@Parameter(description = "商品ID", required = true) @PathVariable Long id) {
        PayProduct payProduct = this.payProductService.getById(id);
        return ResponseResult.ok(PayProductConvert.INSTANCE.convert(payProduct));
    }

    /**
     * 商品分页查询
     *
     * @param payProductPageDto 查询条件
     * @return 商品分页列表
     */
    @Operation(summary = "商品分页查询")
    @PreAuthorize("hasAuthority('pay:product:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<PayProductVo>> page(@Validated @RequestBody PayProductPageDto payProductPageDto) {
        PageResult<PayProductVo> page = this.payProductService.page(payProductPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 商品导出数据
     *
     * @param payProductPageDto 查询条件
     */
    @Operation(summary = "商品导出")
    @PreAuthorize("hasAuthority('pay:product:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody PayProductPageDto payProductPageDto) {
        this.payProductService.export(payProductPageDto);
    }

}