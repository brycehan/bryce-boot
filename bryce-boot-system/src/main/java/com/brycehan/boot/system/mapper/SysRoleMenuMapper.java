package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统角色菜单关系Mapper接口
 *
 * @since 2022/5/15
 * @author Bryce Han
 */
@Mapper
public interface SysRoleMenuMapper extends BryceBaseMapper<SysRoleMenu> {

}
