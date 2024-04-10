package com.brycehan.boot.ma.controller;

import com.brycehan.boot.api.system.SysUploadFileApi;
import com.brycehan.boot.api.system.vo.SysUploadFileVo;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import com.brycehan.boot.ma.convert.MaUserConvert;
import com.brycehan.boot.ma.dto.MaLoginDto;
import com.brycehan.boot.ma.dto.MaUserDto;
import com.brycehan.boot.ma.dto.MaUserPageDto;
import com.brycehan.boot.ma.entity.MaUser;
import com.brycehan.boot.ma.service.MaUserService;
import com.brycehan.boot.ma.vo.MaUserLoginVo;
import com.brycehan.boot.ma.vo.MaUserProfileVo;
import com.brycehan.boot.ma.vo.MaUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 微信小程序用户API
 *
 * @author Bryce Han
 * @since 2024/04/07
 */
@Slf4j
@Tag(name = "微信小程序用户", description = "maUser")
@RequestMapping("/ma/user")
@RestController
@RequiredArgsConstructor
public class MaUserController {

    private final MaUserService maUserService;
    private final SysUploadFileApi sysUploadFileApi;


    /**
     * 更新微信小程序用户
     *
     * @param maUserDto 微信小程序用户Dto
     * @return 响应结果
     */
    @Operation(summary = "更新微信小程序用户")
    @OperateLog(type = OperateType.UPDATE)
    @PutMapping
    public ResponseResult<MaUserVo> update(@Validated(value = UpdateGroup.class) @RequestBody MaUserDto maUserDto) {
        MaUserVo maUserVo = this.maUserService.update(maUserDto);
        return ResponseResult.ok(maUserVo);
    }

    /**
     * 查询微信小程序用户详情
     *
     * @param id 微信小程序用户ID
     * @return 响应结果
     */
    @Operation(summary = "查询微信小程序用户详情")
    @PreAuthorize("hasAuthority('ma:user:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<MaUserVo> get(@Parameter(description = "微信小程序用户ID", required = true) @PathVariable Long id) {
        MaUser maUser = this.maUserService.getById(id);
        return ResponseResult.ok(MaUserConvert.INSTANCE.convert(maUser));
    }

    /**
     * 分页查询
     *
     * @param maUserPageDto 查询条件
     * @return 微信小程序用户分页列表
     */
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('ma:user:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<MaUserVo>> page(@Validated @RequestBody MaUserPageDto maUserPageDto) {
        PageResult<MaUserVo> page = this.maUserService.page(maUserPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 微信小程序用户导出数据
     *
     * @param maUserPageDto 查询条件
     */
    @Operation(summary = "微信小程序用户导出")
    @PreAuthorize("hasAuthority('ma:user:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody MaUserPageDto maUserPageDto) {
        this.maUserService.export(maUserPageDto);
    }

    /**
     * 手机号快捷登录
     *
     * @param maLoginDto 登录dto
     * @return 响应结果
     */
    @Operation(summary = "手机号快捷登录")
    @PostMapping(path = "/login")
    public ResponseResult<MaUserLoginVo> login(@Validated @RequestBody MaLoginDto maLoginDto) throws WxErrorException {
        // 保存用户信息
        MaUserLoginVo loginVo = this.maUserService.login(maLoginDto);
        return ResponseResult.ok(loginVo);
    }

    /**
     * 获取用户信息
     *
     * @return 响应结果
     */
    @Operation(summary = "获取用户信息")
    @GetMapping(path = "/profile")
    public ResponseResult<MaUserProfileVo> profile() {
        String openId = LoginUserContext.currentOpenId();
        MaUser maUser = this.maUserService.getByOpenId(openId);

        MaUserProfileVo profileVo = new MaUserProfileVo();
        BeanUtils.copyProperties(maUser, profileVo);

        return ResponseResult.ok(profileVo);
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

        String moduleName = "ma/avatar";
        try {
            SysUploadFileVo uploadFileVo = this.sysUploadFileApi.upload(file, moduleName);
            if (uploadFileVo != null) {
                String avatar = uploadFileVo.getUrl();
                String openId = LoginUserContext.currentOpenId();
                MaUser maUser = this.maUserService.getByOpenId(openId);
                maUser.setAvatar(avatar);
                this.maUserService.updateById(maUser);
                return ResponseResult.ok(uploadFileVo);
            }
        } catch (Exception e) {
            log.error("上传文件失败，{}", e.getMessage());
            throw new RuntimeException("上传头像失败");
        }

        return ResponseResult.error("出现错误");
    }

}