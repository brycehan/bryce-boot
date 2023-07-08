package com.brycehan.boot.system.controller;

import com.brycehan.boot.common.annotation.Log;
import com.brycehan.boot.common.base.http.HttpResponseStatusEnum;
import com.brycehan.boot.common.base.http.ResponseResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.enums.BusinessType;
import com.brycehan.boot.common.validator.group.AddGroup;
import com.brycehan.boot.common.validator.group.UpdateGroup;
import com.brycehan.boot.system.context.LoginUserContext;
import com.brycehan.boot.system.dto.SysUserPageDto;
import com.brycehan.boot.system.dto.SysUserStatusDto;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.service.SysUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

/**
 * 系统用户控制器
 *
 * @author Bryce Han
 * @since 2022/05/14
 */
@Tag(name = "sysUser", description = "系统用户API")
@RequestMapping("/system/sysUser")
@RestController
public class SysUserController {

    private final SysUserService sysUserService;

    private final SysRoleService sysRoleService;

    private final PasswordEncoder passwordEncoder;

    public SysUserController(SysUserService sysUserService, SysRoleService sysRoleService, PasswordEncoder passwordEncoder) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * 保存系统用户
     *
     * @param sysUser 系统用户
     * @return 响应结果
     */
    @Operation(summary = "保存系统用户")
    @Secured("system:user:add")
    @PostMapping
    public ResponseResult<Void> add(@Parameter(description = "系统用户", required = true)
                                    @Validated(value = AddGroup.class) @RequestBody SysUser sysUser) {
        sysUser.setId(IdGenerator.generate());
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));

        this.sysUserService.save(sysUser);
        return ResponseResult.ok();
    }

    /**
     * 更新系统用户
     *
     * @param sysUser 系统用户
     * @return 响应结果
     */
    @Operation(summary = "更新系统用户")
    @Secured(value = "system:user:edit")
    @PatchMapping
    public ResponseResult<Void> update(@Parameter(description = "系统用户实体", required = true)
                                       @Validated(value = UpdateGroup.class) @RequestBody SysUser sysUser) {
        this.sysUserService.updateById(sysUser);
        return ResponseResult.ok();
    }

    /**
     * 删除系统用户
     *
     * @param ids 用户ID
     * @return 响应结果
     */
    @Operation(summary = "删除系统用户")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @Secured(value = "system:user:remove")
    @DeleteMapping(path = "/{ids}")
    public ResponseResult<Void> deleteById(@Parameter(description = "系统用户IDs", required = true) @PathVariable Long[] ids) {
        if(ArrayUtils.contains(ids, LoginUserContext.currentUserId())){
            return ResponseResult.error(HttpResponseStatusEnum.HTTP_FORBIDDEN);
        }
        this.sysUserService.removeByIds(Arrays.asList(ids));
        return ResponseResult.ok();
    }

    /**
     * 根据系统用户ID查询系统用户信息
     *
     * @param id 用户ID
     * @return 响应结果
     */
    @Operation(summary = "根据系统用户ID查询系统用户详情")
    @Secured(value = "system:user:query")
    @GetMapping(path = { "/item/{id}"})
    public ResponseResult<SysUser> getById(@Parameter(description = "系统用户ID", required = true) @PathVariable String id) {
        SysUser sysUser = this.sysUserService.getById(id);
        sysUser.setPassword(null);
        return ResponseResult.ok(sysUser);
    }

    /**
     * 根据当前账号查询系统用户信息
     *
     * @return 响应结果
     */
    @Operation(summary = "根据系统用户ID查询系统用户详情")
    @Secured(value = "system:user:query")
    @GetMapping(path = {"/item"})
    public ResponseResult<SysUser> getById() {
        SysUser sysUser = this.sysUserService.getById(LoginUserContext.currentUserId());
        sysUser.setPassword(null);
        return ResponseResult.ok(sysUser);
    }

    /**
     * 分页查询
     *
     * @param sysUserPageDto 查询条件
     * @return 分页系统用户
     */
    @Operation(summary = "分页查询")
    @Secured(value = "system:user:list")
    @GetMapping(path = "/page")
    public ResponseResult<PageInfo<SysUser>> page(@Parameter(description = "查询信息", required = true) @RequestBody SysUserPageDto sysUserPageDto) {
        PageInfo<SysUser> pageInfo = this.sysUserService.page(sysUserPageDto);
        return ResponseResult.ok(pageInfo);
    }

    /**
     * 导出系统用户
     *
     * @param response 响应
     * @param sysUser 系统用户
     */
    @Operation(summary = "导出用户")
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @Secured(value = "system:user:export")
    @PostMapping(path = "/export")
    public void export(HttpServletResponse response, SysUser sysUser){

    }

    /**
     * 导入系统用户
     *
     * @param file 上传的文件
     * @param updateSupport 更新支持
     */
    @Operation(summary = "导入系统用户")
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
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
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @Secured(value = "system:user:resetPassword")
    @PostMapping(path = "/resetPassword")
    public ResponseResult<Void> resetPassword(@RequestBody SysUser sysUser) {
        sysUserService.checkUserAllowed(sysUser);
        // todo checkUserDataScope();
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
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
    @Log(title = "修改用户状态", businessType = BusinessType.UPDATE)
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
    public ResponseResult authRole(@PathVariable(value = "userId") String userId){
        SysUser sysUser = this.sysUserService.getById(userId);
        List<SysRole> strings = this.sysRoleService.selectRolesByUserId(userId);
//        Set<SysRole> stringss = Sets.newHashSet(strings);
        // todo
        return ResponseResult.ok();
    }

    @Secured(value = "system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping(path = "/authRole")
    public ResponseResult<Void> insertAuthRole(String userId, Long[] roleIds){
        this.sysUserService.insertAuthRole(userId, roleIds);
        return ResponseResult.ok();
    }


//    @Secured(value = "system:user:list")
//    @GetMapping(path = "/deptTree")
//    public ResponseResult deptTree(){
//
//    }
}

