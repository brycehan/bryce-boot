package com.brycehan.boot.common.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.common.base.convert.OrderItemConvert;
import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.base.entity.OrderItemDto;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.service.BaseService;
import com.brycehan.boot.common.util.JsonUtils;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @author Bryce Han
 * @since 2022/9/16
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    /**
     * 获取分页对象
     *
     * @param pageDto 分页Dto
     * @return 分页对象
     */
    protected IPage<T> getPage(BasePageDto pageDto) {
        Page<T> page = new Page<>(pageDto.getCurrent(), pageDto.getPageSize());
        log.debug("BaseServiceImpl.getPage 参数：{}", JsonUtils.writeValueAsString(pageDto));

        // 排序
        if (CollectionUtils.isNotEmpty(pageDto.getOrderItems())) {
            // 过滤无效排序参数
            List<OrderItemDto> orderItems = pageDto.getOrderItems()
                    .stream()
                    .filter(orderItem -> hasField(pageDto.getClass(), orderItem.getColumn()))
                    .toList();

            log.debug("排序参数: {}", JsonUtils.writeValueAsString(orderItems));

            if (CollectionUtils.isNotEmpty(orderItems)) {
                // 驼峰转下划线命名
                orderItems.forEach(orderItem -> orderItem.setColumn(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, orderItem.getColumn())));
                page.addOrder(OrderItemConvert.INSTANCE.convert(orderItems));
            }
        }else if (hasField(pageDto.getClass(), DataConstants.DEFAULT_SORT_COLUMN)) {
            // 默认排序
            OrderItemDto orderItemDto = OrderItemDto.builder()
                    .column(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, DataConstants.DEFAULT_SORT_COLUMN))
                    .asc(DataConstants.DEFAULT_SORT_IS_ASC)
                    .build();
            page.addOrder(OrderItemConvert.INSTANCE.convert(orderItemDto));
        }

        return page;
    }


    /**
     * 判断一个类中是否含有某个属性字段
     *
     * @param c         类对象
     * @param fieldName 属性名称
     * @return 布尔值
     */
    public boolean hasField(@SuppressWarnings("rawtypes") Class c, String fieldName) {
        try {
            c.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }
}
