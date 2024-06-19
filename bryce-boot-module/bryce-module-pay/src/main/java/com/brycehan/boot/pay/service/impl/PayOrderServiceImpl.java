package com.brycehan.boot.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.pay.common.OrderNoUtils;
import com.brycehan.boot.pay.common.OrderStatus;
import com.brycehan.boot.pay.common.PayType;
import com.brycehan.boot.pay.entity.convert.PayOrderConvert;
import com.brycehan.boot.pay.entity.dto.PayOrderPageDto;
import com.brycehan.boot.pay.entity.po.PayOrder;
import com.brycehan.boot.pay.entity.po.PayProduct;
import com.brycehan.boot.pay.entity.vo.PayOrderVo;
import com.brycehan.boot.pay.mapper.PayOrderMapper;
import com.brycehan.boot.pay.mapper.PayProductMapper;
import com.brycehan.boot.pay.service.PayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;


/**
 * 订单服务实现
 *
 * @author Bryce Han
 * @since 2024/02/27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl extends BaseServiceImpl<PayOrderMapper, PayOrder> implements PayOrderService {

    private final PayProductMapper payProductMapper;

    @Override
    public PageResult<PayOrderVo> page(PayOrderPageDto payOrderPageDto) {

        IPage<PayOrder> page = this.baseMapper.selectPage(getPage(payOrderPageDto), getWrapper(payOrderPageDto));

        return new PageResult<>(page.getTotal(), PayOrderConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param payOrderPageDto 订单分页dto
     * @return 查询条件Wrapper
     */
    private LambdaQueryWrapper<PayOrder> getWrapper(PayOrderPageDto payOrderPageDto){
        LambdaQueryWrapper<PayOrder> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
    }

    @Override
    public void export(PayOrderPageDto payOrderPageDto) {
        List<PayOrder> payOrderList = this.baseMapper.selectList(getWrapper(payOrderPageDto));
        List<PayOrderVo> payOrderVoList = PayOrderConvert.INSTANCE.convert(payOrderList);
        ExcelUtils.export(PayOrderVo.class, "订单_".concat(DateTimeUtils.today()), "订单", payOrderVoList);
    }

    @Override
    public PayOrder createOrderByProductId(Long productId, PayType payType) {

        // 查找已存在但未支付的订单
        PayOrder payOrder = this.getNoPayOrderByProductId(productId, payType);
        if(payOrder != null) {
            return payOrder;
        }

        // 获取商品信息
        PayProduct payProduct = this.payProductMapper.selectById(productId);

        // 生成订单
        payOrder = new PayOrder();
        payOrder.setId(IdGenerator.nextId());
        payOrder.setTitle(payProduct.getTitle());
        payOrder.setOrderNo(OrderNoUtils.getOrderNo()); // 订单号
        payOrder.setProductId(productId);
        payOrder.setTotalFee(payProduct.getPrice());
        payOrder.setOrderStatus(OrderStatus.NO_PAY.getType());
        payOrder.setPaymentType(payType.getValue());
        payOrder.setUserId(LoginUserContext.currentUserId());

        this.baseMapper.insert(payOrder);

        return payOrder;
    }

    /**
     * 根据商品ID查询未支付订单，防止重复创建订单对象
     *
     * @param productId 商品ID
     * @param payType 支付类型
     * @return 未支付订单
     */
    private PayOrder getNoPayOrderByProductId(Long productId, PayType payType) {

        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrder::getProductId, productId);
        queryWrapper.eq(PayOrder::getOrderStatus, OrderStatus.NO_PAY.getType());
        queryWrapper.eq(PayOrder::getPaymentType, payType.getValue());
        queryWrapper.eq(PayOrder::getUserId, LoginUserContext.currentUserId());

        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void saveCodeUrl(Long id, String codeUrl) {

        PayOrder payOrder = new PayOrder();
        payOrder.setId(id);
        payOrder.setCodeUrl(codeUrl);

        this.baseMapper.updateById(payOrder);
    }

    @Override
    public PageResult<PayOrderVo> userPage(PayOrderPageDto payOrderPageDto) {
        LambdaQueryWrapper<PayOrder> wrapper = getWrapper(payOrderPageDto);
        wrapper.eq(PayOrder::getUserId, LoginUserContext.currentUserId());

        IPage<PayOrder> page = this.baseMapper.selectPage(getPage(payOrderPageDto), wrapper);

        return new PageResult<>(page.getTotal(), PayOrderConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 根据订单号获取订单状态
     * @param orderNo 订单号
     * @return 订单状态
     */
    @Override
    public String getOrderStatus(String orderNo) {
        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrder::getOrderNo, orderNo);

        PayOrder payOrder = this.baseMapper.selectOne(queryWrapper);
        if(payOrder == null) {
            return null;
        }

        return payOrder.getOrderStatus();
    }

    @Override
    public void updateStatusByOrderNo(String orderNo, OrderStatus orderStatus) {
        log.info("更新订单状态，订单号：{}，订单状态：{}", orderNo, orderStatus.getType());

        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrder::getOrderNo, orderNo);

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderStatus(orderStatus.getType());

        this.baseMapper.update(payOrder, queryWrapper);
    }

    @Override
    public List<PayOrder> getNoPayOrderByDuration(int minutes, PayType payType) {

        Instant instant = Instant.now().minus(Duration.ofMinutes(minutes));

        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrder::getOrderStatus, OrderStatus.NO_PAY.getType());
        queryWrapper.le(PayOrder::getCreatedTime, instant);
        queryWrapper.eq(PayOrder::getPaymentType, payType.getValue());

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public PayOrder getOrderByOrderNo(String orderNo) {

        LambdaQueryWrapper<PayOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PayOrder::getOrderNo, orderNo);

        return this.baseMapper.selectOne(queryWrapper);
    }

}
