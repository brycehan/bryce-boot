package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户Mapper接口
 *
 * @since 2022/5/08
 * @author Bryce Han
 */
@Mapper
public interface SysUserMapper extends BryceBaseMapper<SysUser> {

    default SysUser getByUsername(String username) {
        return this.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    default SysUser getByPhone(String phone) {
        return this.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone));
    }

    List<SysUser> roleUserList(Map<String, Object> params);

    List<SysUser> list(Map<String, Object> params);

    default SysUser getByEmail(String email) {
        return this.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getEmail, email));
    }
}
