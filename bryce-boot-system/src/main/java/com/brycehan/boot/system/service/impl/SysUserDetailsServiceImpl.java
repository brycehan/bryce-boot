package com.brycehan.boot.system.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.UserResponseStatus;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.LocationUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.TokenUtils;
import com.brycehan.boot.system.entity.convert.SysUserConvert;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.SysAuthorityService;
import com.brycehan.boot.system.service.SysOrgService;
import com.brycehan.boot.system.service.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 系统用户详情服务实现
 *
 * @author Bryce Han
 * @since 2023/10/7
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserDetailsServiceImpl implements SysUserDetailsService {

    private final SysAuthorityService sysAuthorityService;

    private final SysOrgService sysOrgService;

    @Override
    public UserDetails getUserDetails(SysUser sysUser) {
        // 账号不可用
        if (sysUser.getStatus() == StatusType.DISABLE) {
            log.info("登录用户：{}已被锁定.", sysUser.getUsername());
            throw new ServerException(UserResponseStatus.USER_ACCOUNT_LOCKED);
        }

        LoginUser loginUser = SysUserConvert.INSTANCE.convertLoginUser(sysUser);

        // 预处理登录用户
        prepare(loginUser);

        // 用户权限集合
        Set<String> authoritySet = sysAuthorityService.findAuthority(sysUser, true);
        loginUser.setRoles(sysUser.getRoles());
        loginUser.setAuthoritySet(authoritySet);

        return loginUser;
    }

    /**
     * 预处理登录用户
     *
     * @param loginUser 登录用户
     */
    private void prepare(LoginUser loginUser) {
        // 获取客户端信息
        String userAgent = ServletUtils.getRequest().getHeader(HttpHeaders.USER_AGENT);
        UserAgent parser = UserAgentUtil.parse(userAgent);

        // 获取客户端操作系统
        String os = parser.getOs().getName();
        // 获取客户端浏览器
        String browser = parser.getBrowser().getName();

        // 获取客户端IP和对应登录位置
        String ip = IpUtils.getIp(ServletUtils.getRequest());
        String loginLocation = LocationUtils.getLocationByIP(ip);

        // 设置来源客户端
        loginUser.setSourceClientType(TokenUtils.getSourceClient(ServletUtils.getRequest()));
        loginUser.setUserAgent(userAgent);
        loginUser.setOs(os);
        loginUser.setBrowser(browser);
        loginUser.setLoginIp(ip);
        loginUser.setLoginLocation(loginLocation);

        // 机构名称
        loginUser.setOrgName(sysOrgService.getOrgNameById(loginUser.getOrgId()));
        // 用户所有机构的下级机构集合
        List<Long> subOrgIds = sysOrgService.getSubOrgIds(loginUser.getOrgId());
        loginUser.setSubOrgIds(new HashSet<>(subOrgIds));
    }

}
