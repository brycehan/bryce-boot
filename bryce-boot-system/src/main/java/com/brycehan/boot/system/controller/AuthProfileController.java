package com.brycehan.boot.system.controller;

import com.brycehan.boot.api.system.SysUploadFileApi;
import com.brycehan.boot.api.system.vo.SysUploadFileVo;
import com.brycehan.boot.common.base.dto.ProfileDto;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.base.vo.ProfileVo;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.common.base.context.LoginUser;
import com.brycehan.boot.common.base.context.LoginUserContext;
import com.brycehan.boot.system.dto.SysUserPasswordDto;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * 用户个人中心API
 *
 * @since 2022/10/31
 * @author Bryce Han
 */
@Slf4j
@Tag(name = "用户个人中心")
@RequestMapping(path = "/auth/profile")
@RestController
@RequiredArgsConstructor
public class AuthProfileController {

    private final SysUserService sysUserService;

    private final JwtTokenProvider jwtTokenProvider;

    private final SysUploadFileApi sysUploadFileApi;

    /**
     * 个人信息
     *
     * @return 响应结果
     */
    @Operation(summary = "个人信息")
    @GetMapping
    public ResponseResult<ProfileVo> profile() {
        Long userId = LoginUserContext.currentUserId();
        SysUser sysUser = this.sysUserService.getById(userId);

        ProfileVo profileVo = new ProfileVo();
        BeanUtils.copyProperties(sysUser, profileVo);
        profileVo.setNickname(sysUser.getFullName());

        return ResponseResult.ok(profileVo);
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
    public ResponseResult<ProfileVo> update(@RequestBody ProfileDto profile) {
        // 校验
        LoginUser loginUser = LoginUserContext.currentUser();
        assert loginUser != null;

        SysUser sysUser = this.sysUserService.getById(loginUser.getId());
        SysUser user = new SysUser();
        BeanUtils.copyProperties(profile, user);
        user.setFullName(profile.getNickname());

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
            ProfileVo profileVo = new ProfileVo();
            BeanUtils.copyProperties(user, profileVo);
            profileVo.setNickname(user.getFullName());

            return ResponseResult.ok(profileVo);
        }

        return ResponseResult.error(UserResponseStatus.USER_PROFILE_ALTER_ERROR);
    }

    /**
     * 更新头像
     *
     * @param file 上传文件
     * @return 响应结果
     */
    @Operation(summary = "更新头像")
    @OperateLog(type = OperateType.INSERT)
    @PostMapping(path = "/avatar")
    public ResponseResult<?> uploadAvatar(@RequestParam MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseResult.error("上传文件不能为空");
        }

        String moduleName = "system/avatar";
        try {
            SysUploadFileVo uploadFileVo = this.sysUploadFileApi.upload(file, moduleName);
            if (uploadFileVo != null) {
                String avatar = uploadFileVo.getUrl();
                Long id = LoginUserContext.currentUserId();
                SysUser sysUser = this.sysUserService.getById(id);
                sysUser.setAvatar(avatar);
                this.sysUserService.updateById(sysUser);

                return ResponseResult.ok(uploadFileVo);
            }
        } catch (Exception e) {
            log.error("上传文件失败，{}", e.getMessage());
            throw new RuntimeException("上传头像失败");
        }

        return ResponseResult.error("出现错误");
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
