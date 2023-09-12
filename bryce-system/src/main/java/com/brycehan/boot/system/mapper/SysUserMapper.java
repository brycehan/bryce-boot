package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.dto.SysUserPageDto;
import com.brycehan.boot.system.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户Mapper接口
 *
 * @author Bryce Han
 * @since 2022/5/08
 */
@Mapper
public interface SysUserMapper extends BryceBaseMapper<SysUser> {

}
