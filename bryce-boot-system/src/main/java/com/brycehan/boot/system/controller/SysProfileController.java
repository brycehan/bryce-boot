package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.response.ResponseResult;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperatedType;
import com.brycehan.boot.system.entity.dto.SysUserInfoDto;
import com.brycehan.boot.system.entity.dto.SysUserPasswordDto;
import com.brycehan.boot.system.entity.vo.SysUserInfoVo;
import com.brycehan.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping(path = "/system/profile")
@RestController
@RequiredArgsConstructor
public class SysProfileController {

    private final SysUserService sysUserService;

    /**
     * 个人信息
     *
     * @return 响应结果
     */
    @Operation(summary = "个人信息")
    @GetMapping
    public ResponseResult<SysUserInfoVo> getUserInfo() {
        Long userId = LoginUserContext.currentUserId();
        SysUserInfoVo sysUserInfoVo = this.sysUserService.getUserInfo(userId);
        return ResponseResult.ok(sysUserInfoVo);
    }

    /**
     * 修改用户个人信息
     *
     * @param sysUserInfoDto 用户个人信息
     * @return 响应结果
     */
    @Operation(summary = "修改用户个人信息")
    @OperateLog(type = OperatedType.UPDATE)
    @PutMapping
    public ResponseResult<Void> updateUserInfo(@RequestBody SysUserInfoDto sysUserInfoDto) {
        this.sysUserService.updateUserInfo(sysUserInfoDto);
        return ResponseResult.ok();
    }

    /**
     * 修改用户头像
     *
     * @param file 用户头像文件
     * @return 响应结果
     */
    @Operation(summary = "修改用户头像")
    @OperateLog(type = OperatedType.UPDATE)
    @PostMapping(path = "/avatar")
    public ResponseResult<String> updateAvatar(@RequestPart MultipartFile file) {
        String avatar = this.sysUserService.updateAvatar(file);
        return ResponseResult.ok(avatar);
    }

    /**
     * 修改密码
     *
     * @param sysUserPasswordDto 系统用户修改密码Dto
     * @return 响应结果
     */
    @Operation(summary = "修改密码")
    @OperateLog(type = OperatedType.UPDATE)
    @PutMapping(path = "/password")
    public ResponseResult<Void> updatePassword(@Validated @RequestBody SysUserPasswordDto sysUserPasswordDto) {
        this.sysUserService.updatePassword(sysUserPasswordDto);
        return ResponseResult.ok();
    }


}
