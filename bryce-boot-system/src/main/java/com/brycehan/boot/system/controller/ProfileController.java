package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.dto.ProfileDto;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import com.brycehan.boot.system.dto.SysUserPasswordDto;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户个人中心API
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Tag(name = "用户个人中心")
@RequestMapping(path = "/profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final SysUserService sysUserService;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 个人信息
     *
     * @return 响应结果
     */
    @Operation(summary = "个人信息")
    @GetMapping
    public ResponseResult<Map<String, Object>> profile() {
        Long userId = LoginUserContext.currentUserId();
        SysUser sysUser = this.sysUserService.getById(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("user", sysUser);

        return ResponseResult.ok(result);
    }

    /**
     * 修改用户个人信息
     *
     * @param profile 用户个人信息
     * @return 响应结果
     */
    @Operation(summary = "修改用户个人信息")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping
    public ResponseResult<Void> update(@RequestBody ProfileDto profile) {
        // 校验
        LoginUser loginUser = LoginUserContext.currentUser();
        assert loginUser != null;

        SysUser sysUser = this.sysUserService.getById(loginUser.getId());
        SysUser user = new SysUser();
        BeanUtils.copyProperties(profile, user);
        user.setId(loginUser.getId());

        // 校验手机号码
        if (!this.sysUserService.checkPhoneUnique(user)) {
            return ResponseResult.error(UserResponseStatus.USER_PROFILE_PHONE_INVALID, null, loginUser.getUsername());
        }
        // 校验邮箱
        if (!this.sysUserService.checkEmailUnique(user)) {
            return ResponseResult.error(UserResponseStatus.USER_PROFILE_EMAIL_INVALID, null, loginUser.getUsername());
        }
        // 更新并更新缓存用户信息
        if (this.sysUserService.updateById(user)) {
            // 更新缓存用户信息
            BeanUtils.copyProperties(profile, sysUser);
            this.jwtTokenProvider.setLoginUser(loginUser);
            return ResponseResult.ok();
        }

        return ResponseResult.error(UserResponseStatus.USER_PROFILE_ALTER_ERROR);
    }

    /**
     * 修改密码
     *
     * @param sysUserPasswordDto 系统用户修改密码 Dto
     * @return 响应结果
     */
    @Operation(summary = "修改密码")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping(path = "/password")
    public ResponseResult<Void> password(@Validated @RequestBody SysUserPasswordDto sysUserPasswordDto) {
        this.sysUserService.updatePassword(sysUserPasswordDto);
        return ResponseResult.ok();
    }

}
