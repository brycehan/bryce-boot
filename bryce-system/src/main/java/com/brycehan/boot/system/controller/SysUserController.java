package com.brycehan.boot.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.HttpResponseStatus;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.validator.SaveGroup;
import com.brycehan.boot.common.validator.UpdateGroup;
import com.brycehan.boot.framework.operatelog.annotation.OperateLog;
import com.brycehan.boot.framework.operatelog.annotation.OperateType;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import com.brycehan.boot.system.convert.SysUserConvert;
import com.brycehan.boot.system.dto.SysUserDto;
import com.brycehan.boot.system.dto.SysUserPageDto;
import com.brycehan.boot.system.dto.SysUserStatusDto;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.service.SysUserPostService;
import com.brycehan.boot.system.service.SysUserRoleService;
import com.brycehan.boot.system.service.SysUserService;
import com.brycehan.boot.system.vo.SysUserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
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
@Tag(name = "系统用户", description = "sysUser")
@RequestMapping("/system/user")
@RestController
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysUserRoleService sysUserRoleService;

    private final SysUserPostService sysUserPostService;

    private final SysRoleService sysRoleService;

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
        if(CollectionUtil.contains(idsDto.getIds(), LoginUserContext.currentUserId())) {
            throw BusinessException.responseStatus(HttpResponseStatus.HTTP_FORBIDDEN);
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
        sysUser.setPassword(null);

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
     * 分页查询
     *
     * @param sysUserPageDto 查询条件
     * @return 系统用户分页列表
     */
    @Operation(summary = "分页查询")
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
     * 导入系统用户
     *
     * @param file 上传的文件
     * @param updateSupport 更新支持
     */
    @Operation(summary = "导入系统用户")
    @OperateLog(type = OperateType.IMPORT)
    @Secured(value = "system:user:import")
    @PostMapping(path = "/importData")
    public void importData(MultipartFile file, boolean updateSupport){

    }

    /**
     * 下载导入模板
     *
     * @param response 响应
     */
    @Operation(summary = "下载导入模板")
    @PostMapping(path = "/importTemplate")
    public void importTemplate(HttpServletResponse response){

    }

    /**
     *
     * @param sysUser
     * @return
     */
    @Operation(summary = "重置密码")
    @OperateLog(type = OperateType.UPDATE)
    @Secured(value = "system:user:resetPassword")
    @PostMapping(path = "/resetPassword")
    public ResponseResult<Void> resetPassword(@RequestBody SysUser sysUser) {
        sysUserService.checkUserAllowed(sysUser);
        // todo checkUserDataScope();
//        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        this.sysUserService.updateById(sysUser);
        return ResponseResult.ok();
    }

    /**
     * 修改用户状态
     *
     * @param sysUserStatusDto 系统用户状态dto
     * @return 响应结果
     */
    @Operation(summary = "修改用户状态")
    @OperateLog(type = OperateType.UPDATE)
    @Secured(value = "system:user:edit")
    @PutMapping(path = "/status")
    public ResponseResult<Void> status(@RequestBody SysUserStatusDto sysUserStatusDto) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserStatusDto, sysUser);
        sysUserService.checkUserAllowed(sysUser);
        // todo checkUserDataScope();
        this.sysUserService.updateById(sysUser);
        return ResponseResult.ok();
    }

    /**
     * 根据用户ID获取授权角色
     *
     * @param userId 用户ID
     * @return 用户的授权角色
     */
    @Secured(value = "system:user:query")
    @GetMapping(path = "/authRole/{userId}")
    public ResponseResult authRole(@PathVariable(value = "userId") Long userId){
        SysUser sysUser = this.sysUserService.getById(userId);
        List<SysRole> strings = this.sysRoleService.selectRolesByUserId(userId);
//        Set<SysRole> stringss = Sets.newHashSet(strings);
        // todo
        return ResponseResult.ok();
    }

    @Secured(value = "system:user:edit")
    @OperateLog(type = OperateType.GRANT)
    @PutMapping(path = "/authRole")
    public ResponseResult<Void> insertAuthRole(Long userId, Long[] roleIds){
        this.sysUserService.insertAuthRole(userId, roleIds);
        return ResponseResult.ok();
    }

}

