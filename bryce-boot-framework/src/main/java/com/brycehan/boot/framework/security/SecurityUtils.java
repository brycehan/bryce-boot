package com.brycehan.boot.framework.security;

import cn.hutool.core.bean.BeanUtil;
import com.brycehan.boot.api.ma.vo.MaUserApiVo;
import com.brycehan.boot.framework.common.SourceClientType;
import com.brycehan.boot.framework.security.context.LoginUser;
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

}
