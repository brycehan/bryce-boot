package com.brycehan.boot.framework.mybatis.service.impl;

import cn.hutool.core.text.NamingCase;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.common.base.convert.OrderItemConvert;
import com.brycehan.boot.common.base.entity.BasePageDto;
import com.brycehan.boot.common.base.entity.OrderItemDto;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.util.JsonUtils;
import com.brycehan.boot.framework.mybatis.interceptor.DataScope;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @since 2022/9/16
 * @author Bryce Han
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
        Page<T> page = new Page<>(pageDto.getCurrent(), pageDto.getSize());
        log.debug("BaseServiceImpl.getPage 参数：{}", JsonUtils.writeValueAsString(pageDto));

        List<OrderItem> orderItems = new ArrayList<>();
        // 处理排序参数
        if (CollectionUtils.isNotEmpty(pageDto.getOrderItems())) {
            // 过滤无效排序参数
            List<OrderItemDto> itemDtoList = pageDto.getOrderItems()
                    .stream()
                    .filter(orderItem -> hasEntityField(pageDto.getClass(), orderItem.getColumn()))
                    .toList();

            log.debug("排序参数: {}", JsonUtils.writeValueAsString(itemDtoList));

            if (CollectionUtils.isNotEmpty(itemDtoList)) {
                // 驼峰转下划线命名
                itemDtoList.forEach(orderItem -> orderItem.setColumn(NamingCase.toUnderlineCase(orderItem.getColumn().toLowerCase())));
                orderItems.addAll(OrderItemConvert.INSTANCE.convert(itemDtoList));
            }
        }

        // 无参数时，若有sort字段，默认按sort排序升序
        if (CollectionUtils.isEmpty(orderItems) && hasEntityField(pageDto.getClass(), DataConstants.DEFAULT_SORT_COLUMN)) {
            orderItems.add(new OrderItem(DataConstants.DEFAULT_SORT_COLUMN, DataConstants.DEFAULT_SORT_IS_ASC));
        }

        // 默认按id降序排序
        if (CollectionUtils.isEmpty(orderItems)) {
            orderItems.add(new OrderItem("id", false));
        }

        page.addOrder(orderItems);

        return page;
    }

    /**
     * 判断一个类中是否含有某个属性字段
     *
     * @param pageDtoClazz pageDto类对象
     * @param fieldName 属性名称
     * @return 布尔值
     */
    public boolean hasEntityField(Class<?> pageDtoClazz, String fieldName) {
        String clazzName = pageDtoClazz.getName();
        if(clazzName.endsWith("PageDto")) {
            clazzName = clazzName.substring(0, clazzName.indexOf("PageDto"));
        }
        clazzName = clazzName.replace(".dto.", ".entity.");
        try {
            return hasField(Class.forName(clazzName), fieldName);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    /**
     * 判断一个类中是否含有某个属性字段
     *
     * @param clazz 类对象
     * @param fieldName 属性名称
     * @return 布尔值
     */
    public boolean hasField(Class<?> clazz, String fieldName) {
        try {
            clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            return false;
        }
        return true;
    }

    /**
     * MybatisPlus数据权限
     *
     * @param queryWrapper 数据权限封装
     */
    protected void dataScopeWrapper(LambdaQueryWrapper<T> queryWrapper) {
        DataScope dataScope = getDataScope(null, null);
        if(dataScope != null) {
            queryWrapper.apply(dataScope.getSqlFilter());
        }
    }

    /**
     * 原生SQL数据权限
     *
     * @param tableAlias 表别名，多表关联时，需要填写表别名
     * @param orgIdAlias 机构ID别名，null表示org_id
     * @return 数据权限
     */
    protected DataScope getDataScope(String tableAlias, String orgIdAlias) {
        LoginUser loginUser = LoginUserContext.currentUser();
        // 如果是超级管理员，则不进行数据过滤
        assert loginUser != null;
        if(loginUser.getSuperAdmin()) {
            return null;
        }

        // 如果为null，则设置成空字符串
        if(tableAlias == null) {
            tableAlias = "";
        }

        // 获取表的别名
        if(StrUtil.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        // 数据权限范围
        Set<Long> dataScopeSet = loginUser.getDataScopeSet();
        // 全部数据权限
        if(dataScopeSet == null) {
            return null;
        }

        // 数据过滤
        if(dataScopeSet.size() > 0) {
            if(StrUtil.isBlank(orgIdAlias)) {
                orgIdAlias = "org_id";
            }
            sqlFilter.append(tableAlias).append(orgIdAlias);

            sqlFilter.append(" in (").append(StrUtil.join(",", dataScopeSet)).append(")");

            sqlFilter.append(" or ");
        }

        // 查询本人数据
        sqlFilter.append(tableAlias).append("created_user_id").append("=").append(loginUser.getId());

        sqlFilter.append(")");

        return new DataScope(sqlFilter.toString());
    }

}
