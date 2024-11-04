package com.brycehan.boot.monitor.controller;

import cn.hutool.core.collection.ListUtil;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.constant.CacheConstants;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.response.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.monitor.entity.dto.OnlineUserPageDto;
import com.brycehan.boot.monitor.entity.vo.OnlineUserVo;
import com.brycehan.boot.system.service.SysOrgService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 在线用户API
 *
 * @since 2023/10/12
 * @author Bryce Han
 */
@Tag(name = "在线用户")
@RestController
@RequestMapping(path = "/system/onlineUser")
@RequiredArgsConstructor
public class OnlineUserController {

    private final RedisTemplate<String, LoginUser> redisTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final SysOrgService sysOrgService;

    /**
     * 在线用户分页查询
     *
     * @param onlineUserPageDto 查询条件
     * @return 在线用户分页列表
     */
    @Operation(summary = "在线用户分页查询")
    @PreAuthorize("hasAuthority('monitor:onlineUser:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<OnlineUserVo>> page(@Validated @RequestBody OnlineUserPageDto onlineUserPageDto) {
        // 获取登录用户的全部 key
        String patternKeys = CacheConstants.LOGIN_USER_KEY.concat("*");
        Set<String> keys = this.redisTemplate.keys(patternKeys);

        // 逻辑分页
        List<String> keyList = ListUtil.page(onlineUserPageDto.getCurrent() - 1, onlineUserPageDto.getSize(), ListUtil.toList(keys));

        // 分页数据
        List<OnlineUserVo> list = new ArrayList<>();
        keyList.forEach(key -> {
            LoginUser loginUser = this.redisTemplate.opsForValue()
                    .get(key);
            if(loginUser != null) {
                OnlineUserVo onlineUserVo = new OnlineUserVo();
                BeanUtils.copyProperties(loginUser, onlineUserVo);
                list.add(onlineUserVo);
            }
        });

        // 处理机构名称
        Map<Long, String> orgNames = this.sysOrgService.getOrgNamesByIds(list.stream().map(OnlineUserVo::getOrgId).toList());
        list.forEach(onlineUserVo -> onlineUserVo.setOrgName(orgNames.get(onlineUserVo.getOrgId())));

        assert keys != null;
        return ResponseResult.ok(new PageResult<>(keys.size(), list));
    }

    /**
     * 强制退出
     *
     * @param userKey 会话存储Key
     * @return 响应结果
     */
    @Operation(summary = "强制退出")
    @OperateLog(type = OperateType.FORCE_QUIT)
    @PreAuthorize("hasAuthority('monitor:onlineUser:delete')")
    @DeleteMapping(path = "/{userKey}")
    public ResponseResult<Void> delete(@PathVariable String userKey) {

        if(StringUtils.isNotBlank(userKey)) {
            // 删除用户信息
            this.jwtTokenProvider.deleteLoginUser(userKey);
        }

        return ResponseResult.ok();
    }

}
