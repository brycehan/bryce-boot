package com.brycehan.boot.system.api;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.api.system.BpmUserApi;
import com.brycehan.boot.api.system.vo.BpmUserVo;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.UserResponseStatus;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.po.SysUserPost;
import com.brycehan.boot.system.service.SysUserPostService;
import com.brycehan.boot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统参数 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmUserApiService implements BpmUserApi {

    private final SysUserService sysUserService;
    private final SysUserPostService sysUserPostService;


    @Override
    public BpmUserVo getUser(Long userId) {
        SysUser sysUser = sysUserService.getById(userId);
        return BeanUtil.copyProperties(sysUser, BpmUserVo.class);
    }

    @Override
    public List<BpmUserVo> getUsers(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return List.of();
        }

        List<SysUser> sysUsers = sysUserService.listByIds(userIds);
        return BeanUtil.copyToList(sysUsers, BpmUserVo.class);
    }

    @Override
    public List<BpmUserVo> getUsersByDeptIds(List<Long> deptIds) {
        List<SysUser> sysUsers = sysUserService.getUserListByDeptIds(deptIds);
        return BeanUtil.copyToList(sysUsers, BpmUserVo.class);
    }

    @Override
    public List<BpmUserVo> getUsersByPostIds(List<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return List.of();
        }
        LambdaQueryWrapper<SysUserPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysUserPost::getPostId, postIds);
        List<SysUserPost> sysUserPosts = sysUserPostService.list(queryWrapper);

        List<Long> userIds = sysUserPosts.stream().map(SysUserPost::getUserId).toList();
        if (CollUtil.isEmpty(userIds)) {
            return List.of();
        }

        List<SysUser> sysUsers = sysUserService.listByIds(userIds);
        return BeanUtil.copyToList(sysUsers, BpmUserVo.class);
    }

    @Override
    public void validateUsers(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return;
        }
        // 获得岗位信息
        List<SysUser> users = sysUserService.listByIds(userIds);
        Map<Long, SysUser> userMap = users.stream().collect(Collectors.toMap(SysUser::getId, Function.identity()));
        // 校验
        userIds.forEach(id -> {
            SysUser user = userMap.get(id);
            if (user == null) {
                throw ServerException.of(UserResponseStatus.USER_ACCOUNT_NOT_EXISTS);
            }
            if (!StatusType.ENABLE.equals(user.getStatus())) {
                throw ServerException.of(UserResponseStatus.USER_ACCOUNT_DISABLED, user.getNickname());
            }
        });
    }
}
