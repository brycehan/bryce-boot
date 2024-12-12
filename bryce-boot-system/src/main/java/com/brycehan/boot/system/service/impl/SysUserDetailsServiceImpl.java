package com.brycehan.boot.system.service.impl;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.enums.DataScope;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.base.response.UserResponseStatus;
import com.brycehan.boot.common.util.IpUtils;
import com.brycehan.boot.common.util.LocationUtils;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.framework.security.TokenUtils;
import com.brycehan.boot.system.mapper.SysRoleDataScopeMapper;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysMenuService;
import com.brycehan.boot.system.service.SysOrgService;
import com.brycehan.boot.system.service.SysUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    private final SysMenuService sysMenuService;

    private final SysOrgService sysOrgService;

    private final SysRoleMapper sysRoleMapper;

    private final SysRoleDataScopeMapper sysRoleDataScopeMapper;

    @Override
    public UserDetails getUserDetails(LoginUser loginUser) {
        // 账号不可用
        loginUser.setEnabled(loginUser.getStatus() == StatusType.ENABLE);
        if (!loginUser.isEnabled()) {
            log.info("登录用户：{}已被锁定.", loginUser.getUsername());
            throw new ServerException(UserResponseStatus.USER_ACCOUNT_LOCKED);
        }

        // 预处理登录用户
        prepare(loginUser);

        // 机构名称
        loginUser.setOrgName(sysOrgService.getOrgNameById(loginUser.getOrgId()));

        // 数据权限范围
        Set<Long> dataScopeSet = this.getDataScope(loginUser);
        loginUser.setDataScopeSet(dataScopeSet);

        // 用户角色集合
        Set<String> roleCodeSet = this.sysRoleMapper.getRoleCodeByUserId(loginUser.getId());
        Set<String> roleSet = roleCodeSet.stream().map(roleCode -> DataConstants.ROLE_PREFIX + roleCode).collect(Collectors.toSet());
        // 用户权限集合
        Set<String> authoritySet = this.sysMenuService.findAuthority(loginUser);
        authoritySet.addAll(roleSet);

        loginUser.setRoleSet(roleSet);
        loginUser.setAuthoritySet(authoritySet);

        return loginUser;
    }

    /**
     * 预处理登录用户
     *
     * @param loginUser 登录用户
     */
    public void prepare(LoginUser loginUser) {
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
    }

    /**
     * 获取数据权限范围
     *
     * @param loginUser 登录用户
     * @return 数据权限范围
     */
    private Set<Long> getDataScope(LoginUser loginUser) {
        Integer dataScope = this.sysRoleMapper.getDataScopeByUserId(loginUser.getId());
        if (dataScope == null) {
            return new HashSet<>();
        }

        if (dataScope.equals(DataScope.ALL.getValue())) {
            // 全部数据范围权限，则返回null
            return null;
        } else if (dataScope.equals(DataScope.ORG_AND_CHILDREN.getValue())) {
            // 本机构及子机构数据
            List<Long> dataScopeList = this.sysOrgService.getSubOrgIds(loginUser.getOrgId());
            // 自定义数据权限范围
            List<Long> dataScopeOrgIds = this.sysRoleDataScopeMapper.getDataScopeOrgIds(loginUser.getId());
            dataScopeList.addAll(dataScopeOrgIds.stream().filter(Objects::nonNull).toList());

            return Set.copyOf(dataScopeList);
        } else if (dataScope.equals(DataScope.ORG_ONLY.getValue())) {
            // 本机构数据
            List<Long> dataScopeList = new ArrayList<>();
            dataScopeList.add(loginUser.getOrgId());
            // 自定义数据权限范围
            dataScopeList.addAll(this.sysRoleDataScopeMapper.getDataScopeOrgIds(loginUser.getId()));

            return Set.copyOf(dataScopeList);
        } else if (dataScope.equals(DataScope.CUSTOM.getValue())) {
            // 自定义数据权限范围
            List<Long> dataScopeOrgIds = this.sysRoleDataScopeMapper.getDataScopeOrgIds(loginUser.getId());
            return Set.copyOf(dataScopeOrgIds);
        }

        // 本人数据
        return new HashSet<>();
    }

}
