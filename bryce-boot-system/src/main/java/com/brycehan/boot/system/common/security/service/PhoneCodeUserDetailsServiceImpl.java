package com.brycehan.boot.system.common.security.service;

import com.brycehan.boot.framework.security.phone.PhoneCodeUserDetailsService;
import com.brycehan.boot.system.entity.convert.SysUserConvert;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 手机验证码登录，用户详情服务实现
 *
 * @author Bryce Han
 * @since 2023/10/7
 */
@Service
@RequiredArgsConstructor
public class PhoneCodeUserDetailsServiceImpl implements PhoneCodeUserDetailsService {

    private final SysUserMapper sysUserMapper;

    private final SysUserDetailsService sysUserDetailsService;

    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        // 查询用户
        SysUser sysUser = this.sysUserMapper.getByPhone(phone);
        if (sysUser == null) {
            throw new UsernameNotFoundException("手机号或验证码错误");
        }

        // 创建用户详情
        return this.sysUserDetailsService.getUserDetails(SysUserConvert.INSTANCE.convertLoginUser(sysUser));
    }
}
