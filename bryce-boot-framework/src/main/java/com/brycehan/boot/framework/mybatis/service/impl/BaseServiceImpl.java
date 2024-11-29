package com.brycehan.boot.framework.mybatis.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.framework.mybatis.interceptor.DataScope;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @since 2022/9/16
 * @author Bryce Han
 */
@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

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
        Assert.notNull(loginUser, "用户未登录");

        // 如果是超级管理员，则不进行数据过滤
        if(loginUser.isSuperAdmin()) {
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
        if(!dataScopeSet.isEmpty()) {
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

    /**
     * 通用时间范围查询方法，支持泛型。
     * @param wrapper 查询条件包装器
     * @param column 获取时间字段的函数引用，例如: Entity::getCreatedTime
     * @param startTime 起始时间
     * @param endTime 结束时间
     * @param <T> 实体类型
     */
    public static <T> void addTimeRangeCondition(LambdaQueryWrapper<T> wrapper, SFunction<T, LocalDateTime> column, LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime != null && endTime != null) {
            wrapper.between(column, startTime, endTime);
        } else if (startTime != null) {
            wrapper.ge(column, startTime);
        } else if (endTime != null) {
            wrapper.le(column, endTime);
        }
    }

}
