package com.brycehan.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.exception.BusinessException;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import com.brycehan.boot.system.convert.SysUserConvert;
import com.brycehan.boot.system.dto.SysRoleUserPageDto;
import com.brycehan.boot.system.dto.SysUserDto;
import com.brycehan.boot.system.dto.SysUserPageDto;
import com.brycehan.boot.system.entity.SysPost;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.entity.SysUserRole;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.*;
import com.brycehan.boot.system.vo.SysUserVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用户服务实现
 *
 * @since 2022/5/08
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService sysUserRoleService;

    private final SysUserPostService sysUserPostService;

    private final SysRoleService sysRoleService;

    private final SysPostService sysPostService;

    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(SysUserDto sysUserDto) {
        // 判断用户名是否存在
        SysUser user = this.baseMapper.getByUsername(sysUserDto.getUsername());
        if (user != null) {
            throw new RuntimeException("账号已经存在");
        }

        // 判断手机号是否存在
        user = this.baseMapper.getByPhone(sysUserDto.getPhone());
        if (user != null) {
            throw new RuntimeException("手机号已经存在");
        }

        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserDto);

        // 密码加密
        sysUser.setPassword(passwordEncoder.encode(sysUserDto.getPassword()));
        sysUser.setId(IdGenerator.nextId());
        sysUser.setSuperAdmin(false);
        sysUser.setTenantAdmin(false);

        // 保存用户
        this.baseMapper.insert(sysUser);

        // 保存用户角色关系
        this.sysUserRoleService.saveOrUpdate(sysUser.getId(), sysUserDto.getRoleIds());

        // 保存用户岗位关系
        this.sysUserPostService.saveOrUpdate(sysUser.getId(), sysUserDto.getPostIds());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(SysUserDto sysUserDto) {
        // 判断手机号是否存在
        SysUser user = this.baseMapper.getByPhone(sysUserDto.getPhone());
        if (user != null && !user.getId().equals(sysUserDto.getId())) {
            throw new RuntimeException("手机号已经存在");
        }

        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserDto);

        // 更新用户
        this.baseMapper.updateById(sysUser);

        // 更新用户角色关系
        this.sysUserRoleService.saveOrUpdate(sysUserDto.getId(), sysUserDto.getRoleIds());

        // 更新用户岗位关系
        this.sysUserPostService.saveOrUpdate(sysUserDto.getId(), sysUserDto.getPostIds());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull)
                .toList();

        if (CollUtil.isNotEmpty(ids)) {
            // 删除用户
            this.baseMapper.deleteBatchIds(ids);

            // 删除用户角色关系
            this.sysUserRoleService.deleteByUserIds(ids);

            // 删除用户岗位关系
            this.sysUserPostService.deleteByUserIds(ids);
        }
    }

    @Override
    public PageResult<SysUserVo> page(SysUserPageDto sysUserPageDto) {

        IPage<SysUser> page = this.baseMapper.selectPage(getPage(sysUserPageDto), getWrapper(sysUserPageDto));

        // 去掉密码
        for (SysUser record : page.getRecords()) {
            record.setPassword(null);
        }
        return new PageResult<>(page.getTotal(), SysUserConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysUserPageDto 系统用户分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysUser> getWrapper(SysUserPageDto sysUserPageDto) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotEmpty(sysUserPageDto.getGender()), SysUser::getGender, sysUserPageDto.getGender());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getType()), SysUser::getType, sysUserPageDto.getType());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getOrgId()), SysUser::getOrgId, sysUserPageDto.getOrgId());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getStatus()), SysUser::getStatus, sysUserPageDto.getStatus());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getTenantId()), SysUser::getTenantId, sysUserPageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(sysUserPageDto.getUsername()), SysUser::getUsername, sysUserPageDto.getUsername());
        wrapper.like(StringUtils.isNotEmpty(sysUserPageDto.getPhone()), SysUser::getPhone, sysUserPageDto.getPhone());

        if (sysUserPageDto.getCreatedTimeStart() != null && sysUserPageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysUser::getCreatedTime, sysUserPageDto.getCreatedTimeStart(), sysUserPageDto.getCreatedTimeEnd());
        } else if (sysUserPageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysUser::getCreatedTime, sysUserPageDto.getCreatedTimeStart());
        } else if (sysUserPageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(SysUser::getCreatedTime, sysUserPageDto.getCreatedTimeEnd());
        }

        return wrapper;
    }

    @Override
    public void export(SysUserPageDto sysUserPageDto) {
        List<SysUser> sysUserList = this.baseMapper.selectList(getWrapper(sysUserPageDto));
        List<SysUserVo> sysUserVoList = SysUserConvert.INSTANCE.convert(sysUserList);
        ExcelUtils.export(SysUserVo.class, "系统用户", "系统用户", sysUserVoList);
    }

    @Override
    public PageResult<SysUserVo> roleUserPage(SysRoleUserPageDto pageDto) {
        // 查询参数
        Map<String, Object> params = BeanUtil.beanToMap(pageDto);
        // 数据权限
        params.put(DataConstants.DATA_SCOPE, getDataScope("bsu", null));

        // 分页查询
        IPage<SysUser> page = getPage(pageDto);
        params.put(DataConstants.PAGE, page);

        // 数据列表
        List<SysUser> list = this.baseMapper.roleUserList(params);

        return new PageResult<>(page.getTotal(), SysUserConvert.INSTANCE.convert(list));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void registerUser(SysUser sysUser) {
        // 1、保存用户
        sysUser.setId(IdGenerator.nextId());
        // 2、添加默认角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(DataConstants.DEFAULT_ROLE_ID);
        this.sysUserRoleService.save(sysUserRole);

        int result = this.baseMapper.insert(sysUser);
        // 3、异步记录注册成功日志
        if (result != 1) {
            throw BusinessException.responseStatus(UserResponseStatus.USER_REGISTER_ERROR);
        }
    }

    @Override
    public boolean checkUsernameUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "username")
                .eq("username", sysUser.getUsername())
                .last("limit 1");
        SysUser user = this.baseMapper.selectOne(queryWrapper);
        Long userId = sysUser.getId() == null ? UserConstants.NULL_USER_ID : sysUser.getId();

        // 修改时，同账号同ID为账号唯一
        return Objects.isNull(user) || userId.equals(user.getId());
    }

    @Override
    public boolean checkPhoneUnique(SysUser sysUser) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .select("id", "phone")
                .eq("phone", sysUser.getPhone())
                .last("limit 1");
        SysUser user = this.baseMapper.selectOne(queryWrapper);
        Long userId = Objects.isNull(sysUser.getId()) ? UserConstants.NULL_USER_ID : sysUser.getId();

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
        SysUser user = this.baseMapper.selectOne(queryWrapper);
        Long userId = Objects.isNull(sysUser.getId()) ? UserConstants.NULL_USER_ID : sysUser.getId();

        // 修改时，同邮箱同ID为邮箱唯一
        return Objects.isNull(user) || userId.equals(user.getId());
    }

    @Override
    public void checkUserAllowed(SysUser sysUser) {
        Long userId = LoginUserContext.currentUserId();
        SysUser user = this.baseMapper.selectById(userId);
        // 1、设置用户角色
        if (CollectionUtils.isEmpty(sysUser.getRoles())) {
            Set<String> sysRoles = this.sysRoleService.selectRolePermissionByUserId(sysUser.getId());
            List<String> codes = sysRoles.stream().map("ROLE_"::concat).toList();
            if (!CollectionUtils.isEmpty(codes)) {
                sysUser.setRoles(new HashSet<>(codes));
            }
        }
        // 1、检查用户权限
        if (!user.getSuperAdmin() && sysUser.getSuperAdmin()) {
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
                .map(SysRole::getName)
                .collect(Collectors.joining("，"));
    }

    @Override
    public String selectUserPostGroup(String username) {
        List<SysPost> sysPosts = this.sysPostService.selectPostsByUsername(username);
        if (CollectionUtils.isEmpty(sysPosts)) {
            return StringUtils.EMPTY;
        }
        return sysPosts.stream()
                .map(SysPost::getName)
                .collect(Collectors.joining("，"));
    }

    @Override
    public boolean updateUserAvatar(Long userId, String avatar) {
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setAvatar(avatar);
        return this.updateById(sysUser);
    }

    @Override
    public void insertAuthRole(Long userId, Long[] roleIds) {

    }
}
