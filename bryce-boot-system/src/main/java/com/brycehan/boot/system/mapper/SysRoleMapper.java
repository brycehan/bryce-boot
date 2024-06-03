package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * 系统角色Mapper接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysRoleMapper extends BryceBaseMapper<SysRole> {

    /**
     * 根据用户ID，获取用户最大的数据范围
     *
     * @param userId 用户ID
     * @return 最大的数据范围权限
     */
    Integer getDataScopeByUserId(Long userId);

    /**
     * 根据用户ID查询角色编码
     *
     * @param userId 用户ID
     * @return 角色编码集合
     */
    Set<String> getRoleCodeByUserId(Long userId);

}
