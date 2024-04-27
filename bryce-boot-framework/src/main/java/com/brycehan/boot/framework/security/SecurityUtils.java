package com.brycehan.boot.framework.security;

import com.brycehan.boot.api.ma.vo.MaUserApiVo;
import com.brycehan.boot.api.system.vo.SysUserApiVo;
import com.brycehan.boot.framework.common.SourceClientType;
import com.brycehan.boot.common.base.context.LoginUser;
import org.springframework.beans.BeanUtils;

import java.util.Collections;

/**
 * @author Bryce Han
 * @since 2024/4/7
 */
public class SecurityUtils {

    /**
     * 创建登录用户
     *
     * @param maUserApiVo 小程序用户
     * @return 登录用户
     */
    public static LoginUser createLoginUser(MaUserApiVo maUserApiVo) {
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(maUserApiVo, loginUser);
        loginUser.setSourceClient(SourceClientType.MINI_APP.value());
        loginUser.setAuthoritySet(Collections.singleton("ROLE_miniApp"));

        return loginUser;
    }

    /**
     * 创建登录用户
     *
     * @param sysUserApiVo 系统用户
     * @return 登录用户
     */
    public static LoginUser createLoginUser(SysUserApiVo sysUserApiVo) {
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUserApiVo, loginUser);
        loginUser.setSourceClient(SourceClientType.APP.value());
        loginUser.setAuthoritySet(Collections.singleton("ROLE_app"));

        return loginUser;
    }

}
