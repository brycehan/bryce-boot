package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.system.entity.po.SysRoleOrg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统角色数据范围Mapper接口
 *
 * @since 2023/09/15
 * @author Bryce Han
 */
@Mapper
public interface SysRoleOrgMapper extends BaseMapper<SysRoleOrg> {

    /**
     * 获取用户的数据权限列表
     *
     * @param userId 用户ID
     * @return 数据权限列表
     */
    List<Long> getDataScopeDeptIds(Long userId);

}
