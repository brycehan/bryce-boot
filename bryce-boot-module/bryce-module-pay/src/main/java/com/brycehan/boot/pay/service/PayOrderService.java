package com.brycehan.boot.pay.service;

import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.pay.common.OrderStatus;
import com.brycehan.boot.pay.common.PayType;
import com.brycehan.boot.pay.entity.dto.PayOrderPageDto;
import com.brycehan.boot.pay.entity.po.PayOrder;
import com.brycehan.boot.pay.entity.vo.PayOrderVo;

import java.util.List;

/**
 * 订单服务
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
public interface PayOrderService extends BaseService<PayOrder> {

    /**
     * 订单分页查询
     *
     * @param payOrderPageDto 查询条件
     * @return 分页信息
     */
    PageResult<PayOrderVo> page(PayOrderPageDto payOrderPageDto);

    /**
     * 订单导出数据
     *
     * @param payOrderPageDto 订单查询条件
     */
    void export(PayOrderPageDto payOrderPageDto);

    /**
     * 通过商品ID创建支付订单
     *
     * @param productId 商品ID
     * @param payType 支付类型
     * @return 支付订单
     */
    PayOrder createOrderByProductId(Long productId, PayType payType);

    /**
     * 存储订单二维码
     *
     * @param id 订单ID
     * @param codeUrl 二维码连接地址
     */
    void saveCodeUrl(Long id, String codeUrl);

    /**
     * 用户订单分页列表
     *
     * @param payOrderPageDto 查询条件
     * @return 订单分页列表
     */
    PageResult<PayOrderVo> userPage(PayOrderPageDto payOrderPageDto);

    /**
     * 根据订单号查询订单状态
     *
     * @param orderNo 订单号
     * @return 订单状态
     */
    String getOrderStatus(String orderNo);

    /**
     * 根据订单号更新订单状态
     *
     * @param orderNo 订单号
     * @param orderStatus 订单状态
     */
    void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus);

    /**
     * 查询创建超过minutes分钟并且未支付的订单
     *
     * @param minutes 分钟数
     * @param payType 支付类型
     * @return 未支付的订单列表
     */
    List<PayOrder> getNoPayOrderByDuration(int minutes, PayType payType);

    /**
     * 根据订单号获取订单
     *
     * @param orderNo 订单号
     * @return 订单信息
     */
    PayOrder getOrderByOrderNo(String orderNo);

}
