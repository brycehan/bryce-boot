package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.common.base.http.UserResponseStatusEnum;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.constant.CommonConstants;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.util.ServletUtils;
import com.brycehan.boot.common.util.MessageUtils;
import com.brycehan.boot.system.context.LoginUserContext;
import com.brycehan.boot.system.dto.SysUserPageDto;
import com.brycehan.boot.system.entity.SysPost;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.entity.SysUserRole;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author Bryce Han
 * @since 2022/5/08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;

    private final SysUserRoleService sysUserRoleService;

    private final SysLoginInfoService sysLoginInfoService;

    private final SysRoleService sysRoleService;

    private final SysPostService sysPostService;

    public SysUserServiceImpl(SysUserMapper sysUserMapper, SysUserRoleService sysUserRoleService, SysLoginInfoService sysLoginInfoService, SysRoleService sysRoleService, SysPostService sysPostService) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleService = sysUserRoleService;
        this.sysLoginInfoService = sysLoginInfoService;
        this.sysRoleService = sysRoleService;
        this.sysPostService = sysPostService;
    }

    @Transactional
    @Override
    public void registerUser(SysUser sysUser) {
        // 1、保存用户
        sysUser.setId(IdGenerator.generate());
        // 2、添加默认角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(DataConstants.DEFAULT_ROLE_ID);
        this.sysUserRoleService.save(sysUserRole);

        int result = this.sysUserMapper.insert(sysUser);
        String userAgent = ServletUtils.getRequest().getHeader("User-Agent");
        // 3、异步记录注册成功日志
        if (result == 1) {
            this.sysLoginInfoService.AsyncRecordLoginInfo(userAgent, sysUser.getUsername(), CommonConstants.REGISTER_SUCCESS, MessageUtils.message("user.register.success"));
        } else {
            throw BusinessException.responseStatus(UserResponseStatusEnum.USER_REGISTER_ERROR);
        }
    }

    /**
     * 查询多条数据
     *
     * @param sysUserPageDto 分页查询数据对象
     * @return 对象列表
     */
    @Override
    public PageInfo<SysUser> page(SysUserPageDto sysUserPageDto) {

        //1.根据page、size初始化分页参数，此时分页插件内部会将这两个参数放到ThreadLocal线程上下文中
        Page<SysUser> page = startPage(sysUserPageDto.getCurrent(), sysUserPageDto.getPageSize());

        /*
          2.紧跟初始化代码，查询业务数据
          2.1.分页拦截器根据page、size参数改写sql语句，生成count并查询
          2.2.分页拦截器执行实际的业务查询
          2.3.分页拦截器将2.1.的总记录数与2.2查询的分页数据封装到1中的page
          2.4.分页拦截器清空ThreadLocal上下文中记录的分页参数信息，防止内存泄漏
         */
        this.sysUserMapper.page(sysUserPageDto);

        //3.转换为PageInfo后返回
        return new PageInfo<>(page);
    }

    @Override
    public boolean checkUsernameUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "username")
                .eq("username", sysUser.getUsername())
                .last("limit 1");
        SysUser user = this.sysUserMapper.selectOne(queryWrapper);
        String userId = Objects.isNull(sysUser.getId()) ? UserConstants.NULL_USER_ID : sysUser.getId();

        // 修改时，同账号同ID为账号唯一
        return Objects.isNull(user) ||  userId.equals(user.getId());
    }

    @Override
    public boolean checkPhoneUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "phone")
                .eq("phone", sysUser.getPhone())
                .last("limit 1");
        SysUser user = this.sysUserMapper.selectOne(queryWrapper);
        String userId = Objects.isNull(sysUser.getId()) ? UserConstants.NULL_USER_ID : sysUser.getId();

        // 修改时，同手机号同ID为手机号唯一
        return Objects.isNull(user) || userId.equals(user.getId());
    }

    @Override
    public boolean checkEmailUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "email")
                .eq("email", sysUser.getEmail())
                .last("limit 1");
        SysUser user = this.sysUserMapper.selectOne(queryWrapper);
        String userId = Objects.isNull(sysUser.getId()) ? UserConstants.NULL_USER_ID : sysUser.getId();

        // 修改时，同邮箱同ID为邮箱唯一
        return Objects.isNull(user) || userId.equals(user.getId());
    }

    @Override
    public void checkUserAllowed(SysUser sysUser) {
        SysUser user = LoginUserContext.currentUser().getSysUser();
        // 1、设置用户角色
        if(CollectionUtils.isEmpty(sysUser.getRoles())){
            Set<String> sysRoles = this.sysRoleService.selectRolePermissionByUserId(sysUser.getId());
            List<String> roleCodes = sysRoles.stream().map("ROLE_"::concat).toList();
            if(!CollectionUtils.isEmpty(roleCodes)){
                sysUser.setRoles(new HashSet<>(roleCodes));
            }
        }
        // 1、检查用户权限
        if(!user.isAdmin() && sysUser.isAdmin()){
            throw new RuntimeException("不允许操作超级管理员用户");
        }
    }

    @Override
    public String selectUserRoleGroup(String username) {
        List<SysRole> sysRoles = this.sysRoleService.selectRolesByUsername(username);
        if (CollectionUtils.isEmpty(sysRoles)) {
            return StringUtils.EMPTY;
        }
        return sysRoles.stream()
                .map(SysRole::getRoleName)
                .collect(Collectors.joining("，"));
    }

    @Override
    public String selectUserPostGroup(String username) {
        List<SysPost> sysPosts = this.sysPostService.selectPostsByUsername(username);
        if (CollectionUtils.isEmpty(sysPosts)) {
            return StringUtils.EMPTY;
        }
        return sysPosts.stream()
                .map(SysPost::getPostName)
                .collect(Collectors.joining("，"));
    }

    @Override
    public boolean updateUserAvatar(String userId, String avatar) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setAvatar(avatar);
        return this.updateById(sysUser);
    }

    @Override
    public void insertAuthRole(String userId, Long[] roleIds) {

    }
}
