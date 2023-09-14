package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户角色关系Mapper接口
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Mapper
public interface SysUserRoleMapper extends BryceBaseMapper<SysUserRole> {

}
