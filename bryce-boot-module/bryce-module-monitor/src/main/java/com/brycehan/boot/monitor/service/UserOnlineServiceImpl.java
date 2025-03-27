package com.brycehan.boot.monitor.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.monitor.entity.convert.UserOnlineConvert;
import com.brycehan.boot.monitor.entity.dto.UserOnlinePageDto;
import com.brycehan.boot.monitor.entity.vo.UserOnlineVo;
import com.brycehan.boot.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 在线用户服务实现类
 *
 * @author Bryce Han
 * @since 2024/12/2
 */
@Service
@RequiredArgsConstructor
public class UserOnlineServiceImpl implements UserOnlineService {

    private final RedisTemplate<String, LoginUser> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final SysDeptService sysDeptService;

    @Override
    public PageResult<UserOnlineVo> pageByUsernameAndLoginIp(UserOnlinePageDto userOnlinePageDto) {
        // 获取登录用户的全部 key
        String patternKeys = CacheConstants.LOGIN_USER_KEY + "*";
        Set<String> keys = redisTemplate.keys(patternKeys);

        if (keys == null) {
            return new PageResult<>(0, new ArrayList<>(0));
        }

        List<UserOnlineVo> list = new ArrayList<>();

        // 处理搜索过滤条件
        for (String key : keys) {
            LoginUser loginUser = redisTemplate.opsForValue().get(key);
            String username = userOnlinePageDto.getUsername();
            String loginIp = userOnlinePageDto.getLoginIp();
            if (StringUtils.hasText(username) && StringUtils.hasText(loginIp)) {
                list.add(getOnlineUserVo(loginUser, username, loginIp));
            } else if (StringUtils.hasText(username)) {
                list.add(getOnlineUserVoByUsername(loginUser, username));
            } else if (StringUtils.hasText(loginIp)) {
                list.add(getOnlineUserVoByLoginIp(loginUser, loginIp));
            }
        }

        list.removeAll(Collections.singleton(null));
        list.sort(Comparator.comparing(UserOnlineVo::getLoginTime).reversed());

        // 逻辑分页
        List<UserOnlineVo> userOnlineVoList = ListUtil.page(userOnlinePageDto.getCurrent() - 1, userOnlinePageDto.getSize(), list);

        // 分页数据，处理部门名称
        Map<Long, String> orgNames = sysDeptService.getOrgNamesByIds(userOnlineVoList.stream().map(UserOnlineVo::getDeptId).toList());
        userOnlineVoList.forEach(onlineUserVo -> onlineUserVo.setOrgName(orgNames.get(onlineUserVo.getDeptId())));

        return new PageResult<>(userOnlineVoList.size(), userOnlineVoList);
    }

    /**
     * 获取在线用户信息
     *
     * @param loginUser 登录用户
     * @param username  用户名
     * @param loginIp   登录IP
     * @return 在线用户信息
     */
    private UserOnlineVo getOnlineUserVo(LoginUser loginUser, String username, String loginIp) {
        if (loginUser == null) {
            return null;
        }
        if (StrUtil.equals(username, loginUser.getUsername()) && StrUtil.equals(loginIp, loginUser.getLoginIp())) {
            return UserOnlineConvert.INSTANCE.convert(loginUser);
        }
        return null;
    }

    /**
     * 获取在线用户信息
     *
     * @param loginUser 登录用户
     * @param username  用户名
     * @return 在线用户信息
     */
    private UserOnlineVo getOnlineUserVoByUsername(LoginUser loginUser, String username) {
        if (loginUser == null) {
            return null;
        }
        if (StrUtil.equals(username, loginUser.getUsername())) {
            return UserOnlineConvert.INSTANCE.convert(loginUser);
        }
        return null;
    }

    /**
     * 获取在线用户信息
     *
     * @param loginUser 登录用户
     * @param loginIp   登录IP
     * @return 在线用户信息
     */
    private UserOnlineVo getOnlineUserVoByLoginIp(LoginUser loginUser, String loginIp) {
        if (loginUser == null) {
            return null;
        }
        if (StrUtil.equals(loginIp, loginUser.getLoginIp())) {
            return UserOnlineConvert.INSTANCE.convert(loginUser);
        }
        return null;
    }

    @Override
    public PageResult<UserOnlineVo> page(UserOnlinePageDto userOnlinePageDto) {
        // 获取登录用户的全部 key
        String patternKeys = CacheConstants.LOGIN_USER_KEY + "*";
        Set<String> keys = redisTemplate.keys(patternKeys);

        if (keys == null) {
            return new PageResult<>(0, new ArrayList<>(0));
        }

        // 逻辑分页
        List<String> keyList = ListUtil.page(userOnlinePageDto.getCurrent() - 1, userOnlinePageDto.getSize(), ListUtil.toList(keys));

        // 分页数据
        List<UserOnlineVo> list = new ArrayList<>();
        keyList.forEach(key -> {
            LoginUser loginUser = redisTemplate.opsForValue().get(key);
            if(loginUser != null) {
                list.add(UserOnlineConvert.INSTANCE.convert(loginUser));
            }
        });
        list.sort(Comparator.comparing(UserOnlineVo::getLoginTime).reversed());

        // 处理部门名称
        Map<Long, String> orgNames = sysDeptService.getOrgNamesByIds(list.stream().map(UserOnlineVo::getDeptId).toList());
        list.forEach(onlineUserVo -> onlineUserVo.setOrgName(orgNames.get(onlineUserVo.getDeptId())));

        return new PageResult<>(keys.size(), list);
    }

    @Override
    public void deleteLoginUser(String userKey) {
        if(StringUtils.hasText(userKey)) {
            // 删除用户信息
            jwtTokenProvider.deleteLoginUser(userKey);
        }
    }

}
