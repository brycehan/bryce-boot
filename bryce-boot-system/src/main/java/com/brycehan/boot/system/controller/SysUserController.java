package com.brycehan.boot.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.brycehan.boot.common.base.context.LoginUserContext;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.system.entity.convert.SysUserConvert;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.vo.SysUserVo;
import com.brycehan.boot.system.service.SysUserPostService;
import com.brycehan.boot.system.service.SysUserRoleService;
import com.brycehan.boot.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 系统用户API
 *
 * @since 2022/05/14
 * @author Bryce Han
 */
@Tag(name = "系统用户")
@RequestMapping("/system/user")
@RestController
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysUserRoleService sysUserRoleService;

    private final SysUserPostService sysUserPostService;

    /**
     * 保存系统用户
     *
     * @param sysUserDto 系统用户Dto
     * @return 响应结果
     */
    @Operation(summary = "保存系统用户")
    @OperateLog(type = OperateType.INSERT)
    @PreAuthorize("hasAuthority('system:user:save')")
    @PostMapping
    public ResponseResult<Void> save(@Validated(value = SaveGroup.class) @RequestBody SysUserDto sysUserDto) {
        this.sysUserService.save(sysUserDto);
        return ResponseResult.ok();
    }

    /**
     * 更新系统用户
     *
     * @param sysUserDto 系统用户Dto
     * @return 响应结果
     */
    @Operation(summary = "更新系统用户")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:user:update')")
    @PutMapping
    public ResponseResult<Void> update(@Validated(value = UpdateGroup.class) @RequestBody SysUserDto sysUserDto) {
        this.sysUserService.update(sysUserDto);
        return ResponseResult.ok();
    }

    /**
     * 删除系统用户
     *
     * @param idsDto ID列表Dto
     * @return 响应结果
     */
    @Operation(summary = "删除系统用户")
    @OperateLog(type = OperateType.DELETE)
    @PreAuthorize("hasAuthority('system:user:delete')")
    @DeleteMapping
    public ResponseResult<Void> delete(@Validated @RequestBody IdsDto idsDto) {
        // 用户不能删除自己的账号
        if (CollectionUtil.contains(idsDto.getIds(), LoginUserContext.currentUserId())) {
            throw new RuntimeException("不能删除当前登录用户");
        }

        this.sysUserService.delete(idsDto);

        return ResponseResult.ok();
    }

    /**
     * 查询系统用户详情
     *
     * @param id 系统用户ID
     * @return 响应结果
     */
    @Operation(summary = "查询系统用户详情")
    @PreAuthorize("hasAuthority('system:user:info')")
    @GetMapping(path = "/{id}")
    public ResponseResult<SysUserVo> get(@Parameter(description = "系统用户ID", required = true) @PathVariable Long id) {
        SysUser sysUser = this.sysUserService.getById(id);
        SysUserVo sysUserVo = SysUserConvert.INSTANCE.convert(sysUser);

        // 用户角色Ids
        List<Long> roleIds = this.sysUserRoleService.getRoleIdsByUserId(id);
        sysUserVo.setRoleIds(roleIds);

        // 用户岗位Ids
        List<Long> postIds = this.sysUserPostService.getPostIdsByUserId(id);
        sysUserVo.setPostIds(postIds);

        return ResponseResult.ok(sysUserVo);
    }

    /**
     * 系统用户分页查询
     *
     * @param sysUserPageDto 查询条件
     * @return 系统用户分页列表
     */
    @Operation(summary = "系统用户分页查询")
    @PreAuthorize("hasAuthority('system:user:page')")
    @PostMapping(path = "/page")
    public ResponseResult<PageResult<SysUserVo>> page(@Validated @RequestBody SysUserPageDto sysUserPageDto) {
        PageResult<SysUserVo> page = this.sysUserService.page(sysUserPageDto);
        return ResponseResult.ok(page);
    }

    /**
     * 系统用户导出数据
     *
     * @param sysUserPageDto 查询条件
     */
    @Operation(summary = "系统用户导出")
    @PreAuthorize("hasAuthority('system:user:export')")
    @PostMapping(path = "/export")
    public void export(@Validated @RequestBody SysUserPageDto sysUserPageDto) {
        this.sysUserService.export(sysUserPageDto);
    }

    /**
     * 导入用户
     *
     * @param file 文件的 Excel 文件
     * @return 响应结果
     */
    @Operation(summary = "导入用户")
    @OperateLog(type = OperateType.IMPORT)
    @PreAuthorize("hasAuthority('system:user:import')")
    @PostMapping(path = "/import")
    public ResponseResult<Void> importByExcel(@RequestParam MultipartFile file) {
        if(file.isEmpty()) {
            return ResponseResult.error("请选择需要上传的文件");
        }

        this.sysUserService.importByExcel(file, "123456");

        return ResponseResult.ok();
    }

    /**
     * 重置密码
     *
     * @param sysResetPasswordDto 需要重置的用户
     * @return 响应结果
     */
    @Operation(summary = "重置密码")
    @OperateLog(type = OperateType.UPDATE)
    @PreAuthorize("hasAuthority('system:user:resetPassword')")
    @PostMapping(path = "/resetPassword")
    public ResponseResult<Void> resetPassword(@Validated @RequestBody SysResetPasswordDto sysResetPasswordDto) {
        this.sysUserService.resetPassword(sysResetPasswordDto);
        return ResponseResult.ok();
    }

    /**
     * 授权用户指定的角色列表
     *
     * @param userId 用户ID
     * @param roleIds 角色列表
     * @return 响应结果
     */
    @Operation(summary = "授权用户角色")
    @OperateLog(type = OperateType.GRANT)
    @PreAuthorize("hasAuthority('system:user:grant')")
    @PutMapping(path = "/authRole")
    public ResponseResult<Void> insertAuthRole(Long userId, List<Long> roleIds) {
        this.sysUserRoleService.saveOrUpdate(userId, roleIds);
        return ResponseResult.ok();
    }

    /**
     * 校验用户账号是否可注册
     *
     * @param username 用户账号
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验用户账号是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/checkUsernameUnique/{username}")
    public ResponseResult<Boolean> checkUsernameUnique(@PathVariable String username) {
        boolean checked = this.sysUserService.checkUsernameUnique(username);
        return ResponseResult.ok(checked);
    }

    /**
     * 校验手机号码是否可注册
     *
     * @param sysUserPhoneDto 手机号码Dto
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验手机号码是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/checkPhoneUnique")
    public ResponseResult<Boolean> checkPhoneUnique(@Validated SysUserPhoneDto sysUserPhoneDto) {
        boolean checked = this.sysUserService.checkPhoneUnique(sysUserPhoneDto);
        return ResponseResult.ok(checked);
    }

    /**
     * 校验用户邮箱是否可注册
     *
     * @param sysUserEmailDto 用户邮箱Dto
     * @return 响应结果，是否可以注册
     */
    @Operation(summary = "校验用户邮箱是否可注册（true：可以注册，false：不可以）")
    @GetMapping(path = "/checkEmailUnique")
    public ResponseResult<Boolean> checkEmailUnique(@Validated SysUserEmailDto sysUserEmailDto) {
        boolean checked = this.sysUserService.checkEmailUnique(sysUserEmailDto);
        return ResponseResult.ok(checked);
    }

}

