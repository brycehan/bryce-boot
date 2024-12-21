package com.brycehan.boot.framework.mybatis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.entity.DataScope;
import com.brycehan.boot.common.entity.vo.RoleVo;
import com.brycehan.boot.common.enums.DataScopeType;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.framework.security.AuthContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
     * @param orgIdAlias 机构ID别名，默认org_id
     * @return 数据权限
     */
    protected DataScope getDataScope(String tableAlias, String orgIdAlias) {
        LoginUser loginUser = LoginUserContext.currentUser();

        // 如果没有登录或者是超级管理员，则不进行数据过滤
        if(loginUser == null || LoginUser.isSuperAdmin(loginUser)) {
            return null;
        }

        String[] auth = AuthContextHolder.getContext();
        // 如果没有配置权限，则不进行数据过滤
        if (auth == null) {
            return null;
        }

        String authority = auth[0]; // 只处理配置的第一个权限

        // 获取表的别名，如果为null，则设置成空字符串
        if(tableAlias == null) {
            tableAlias = "";
        }
        if(StrUtil.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }
        // 获取机构的别名
        if(StrUtil.isBlank(orgIdAlias)) {
            orgIdAlias = "org_id";
        }

        Map<DataScopeType, List<RoleVo>> dataScopeMap = loginUser.getRoles().stream()
                .filter(role -> role.getAuthoritySet().contains(authority))
                .collect(Collectors.groupingBy(RoleVo::getDataScope));

        Set<DataScopeType> dataScopes = dataScopeMap.keySet();

        // 拥有全部数据权限
        if (dataScopes.contains(DataScopeType.ALL)) {
            return null;
        }

        // 数据范围过滤
        StringBuilder sqlFilter = new StringBuilder();

        // 自定义数据权限范围
        if (dataScopes.contains(DataScopeType.CUSTOM)) {
            if (dataScopeMap.get(DataScopeType.CUSTOM).size() > 1) {
                Set<Long> roleIds = dataScopeMap.get(DataScopeType.CUSTOM).stream().map(RoleVo::getId).collect(Collectors.toSet());
                if (CollUtil.isNotEmpty(roleIds)) {
                    sqlFilter.append(tableAlias).append(orgIdAlias);
                    sqlFilter.append(" in (");
                    sqlFilter.append(StrUtil.format(" SELECT org_id FROM brc_sys_role_org WHERE deleted is null and role_id in ({})", StrUtil.join(",", roleIds)));
                    sqlFilter.append(" )");
                }
            } else {
                String finalTableAlias = tableAlias;
                String finalOrgIdAlias = orgIdAlias;
                dataScopeMap.get(DataScopeType.CUSTOM).stream().map(RoleVo::getId).findFirst().ifPresent(roleId -> {
                    sqlFilter.append(finalTableAlias).append(finalOrgIdAlias);
                    sqlFilter.append(" in (");
                    sqlFilter.append(StrUtil.format(" SELECT org_id FROM brc_sys_role_org WHERE deleted is null and role_id = {}", roleId));
                    sqlFilter.append(" )");
                });
            }
        }

        // 本机构及下级机构数据
        if (dataScopes.contains(DataScopeType.ORG_AND_CHILDREN)) {
            if (!sqlFilter.isEmpty()) {
                sqlFilter.append(" or ");
            }
            sqlFilter.append(tableAlias).append(orgIdAlias);
            sqlFilter.append(" in (");
            sqlFilter.append(StrUtil.format(" SELECT org_id FROM brc_sys_org WHERE deleted is null and status = 1 and org_id in ({})", StrUtil.join(",", loginUser.getSubOrgIds())));
            sqlFilter.append(" )");
        } else if (dataScopes.contains(DataScopeType.ORG_ONLY)) { // 本机构数据
            if (!sqlFilter.isEmpty()) {
                sqlFilter.append(" or ");
            }
            sqlFilter.append(tableAlias).append(orgIdAlias);
            sqlFilter.append(" = ").append(loginUser.getOrgId());
        }

        // 查询本人数据
        if (dataScopes.contains(DataScopeType.SELF)) {
            if (!sqlFilter.isEmpty()) {
                sqlFilter.append(" or ");
            }
            sqlFilter.append(tableAlias).append("created_user_id").append(" = ").append(loginUser.getId());
        }

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
