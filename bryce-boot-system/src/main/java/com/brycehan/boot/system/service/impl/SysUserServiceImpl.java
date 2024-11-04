package com.brycehan.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.api.system.StorageApi;
import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.response.UserResponseStatus;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.common.security.RefreshTokenEvent;
import com.brycehan.boot.system.entity.convert.SysUserConvert;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.po.SysUserRole;
import com.brycehan.boot.system.entity.vo.SysUserInfoVo;
import com.brycehan.boot.system.entity.vo.SysUserVo;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

/**
 * 系统用户服务实现
 *
 * @since 2022/5/08
 * @author Bryce Han
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService sysUserRoleService;

    private final SysUserPostService sysUserPostService;

    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final SysOrgService sysOrgService;

    private final SysPostService sysPostService;

    private final SysRoleService sysRoleService;

    private final Executor executor;

    private final StorageApi storageApi;

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

        sysUser.setPassword(passwordEncoder.encode(sysUserDto.getPassword()));
        sysUser.setId(IdGenerator.nextId());
        sysUser.setSuperAdmin(false);

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

        // 如果密码不为空，则进行加密处理
        if (StrUtil.isBlank(sysUser.getPassword())) {
            sysUser.setPassword(null);
        } else {
            sysUser.setPassword(this.passwordEncoder.encode(sysUser.getPassword()));
        }

        // 更新用户
        this.baseMapper.updateById(sysUser);

        // 更新用户角色关系
        this.sysUserRoleService.saveOrUpdate(sysUserDto.getId(), sysUserDto.getRoleIds());

        // 更新用户岗位关系
        this.sysUserPostService.saveOrUpdate(sysUserDto.getId(), sysUserDto.getPostIds());
    }

    @Override
    public SysUserVo get(Long id) {
        SysUser sysUser = this.getById(id);
        SysUserVo sysUserVo = SysUserConvert.INSTANCE.convert(sysUser);

        // 机构名称
        sysUserVo.setOrgName(this.sysOrgService.getOrgNameById(sysUser.getOrgId()));

        // 用户角色Ids
        List<Long> roleIds = this.sysUserRoleService.getRoleIdsByUserId(id);
        sysUserVo.setRoleIds(roleIds);

        // 用户岗位Ids
        List<Long> postIds = this.sysUserPostService.getPostIdsByUserId(id);
        sysUserVo.setPostIds(postIds);

        return sysUserVo;
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
            this.baseMapper.deleteByIds(ids);

            // 删除用户角色关系
            this.sysUserRoleService.deleteByUserIds(ids);

            // 删除用户岗位关系
            this.sysUserPostService.deleteByUserIds(ids);
        }
    }

    @Override
    public PageResult<SysUserVo> page(SysUserPageDto sysUserPageDto) {
        // 查询参数
        Map<String, Object> params = getParams(sysUserPageDto);

        // 分页查询
        IPage<SysUser> page = getPage(sysUserPageDto);
        params.put(DataConstants.PAGE, page);

        // 数据列表
        List<SysUser> list = this.baseMapper.list(params);
        List<SysUserVo> sysUserVoList = SysUserConvert.INSTANCE.convert(list);

        // 处理机构名称
        Map<Long, String> orgNames = this.sysOrgService.getOrgNamesByIds(list.stream().map(SysUser::getOrgId).toList());
        sysUserVoList.forEach(sysUserVo -> sysUserVo.setOrgName(orgNames.get(sysUserVo.getOrgId())));

        return new PageResult<>(page.getTotal(), sysUserVoList);
    }

    private Map<String, Object> getParams(SysUserPageDto sysUserPageDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", sysUserPageDto.getUsername());
        params.put("phone", sysUserPageDto.getPhone());
        params.put("gender", sysUserPageDto.getGender());
        params.put("type", sysUserPageDto.getType());
        params.put("orgId", sysUserPageDto.getOrgId());
        params.put("status", sysUserPageDto.getStatus());
        params.put("createdTimeStart", sysUserPageDto.getCreatedTimeStart());
        params.put("createdTimeEnd", sysUserPageDto.getCreatedTimeEnd());

        // 数据权限
        params.put(DataConstants.DATA_SCOPE, getDataScope("bsu", null));

        return params;
    }

    /**
     * 封装查询条件
     *
     * @param sysUserPageDto 系统用户分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysUser> getWrapper(SysUserPageDto sysUserPageDto) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(sysUserPageDto.getUsername()), SysUser::getUsername, sysUserPageDto.getUsername());
        wrapper.like(StringUtils.isNotEmpty(sysUserPageDto.getPhone()), SysUser::getPhone, sysUserPageDto.getPhone());
        wrapper.eq(StringUtils.isNotEmpty(sysUserPageDto.getGender()), SysUser::getGender, sysUserPageDto.getGender());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getType()), SysUser::getType, sysUserPageDto.getType());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getOrgId()), SysUser::getOrgId, sysUserPageDto.getOrgId());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getStatus()), SysUser::getStatus, sysUserPageDto.getStatus());
        addTimeRangeCondition(wrapper, SysUser::getCreatedTime, sysUserPageDto.getCreatedTimeStart(), sysUserPageDto.getCreatedTimeEnd());

        // 数据权限
        dataScopeWrapper(wrapper);

        return wrapper;
    }

    @Override
    public void export(SysUserPageDto sysUserPageDto) {
        List<SysUser> sysUserList = this.baseMapper.selectList(getWrapper(sysUserPageDto));
        List<SysUserVo> sysUserVoList = SysUserConvert.INSTANCE.convert(sysUserList);
        // 数据字典翻译
        ExcelUtils.transList(sysUserVoList);
        ExcelUtils.export(SysUserVo.class, "系统用户_".concat(DateTimeUtils.today()), "系统用户", sysUserVoList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void importByExcel(MultipartFile file, String password) {
        ExcelUtils.read(file, SysUserExcelDto.class, list -> ((SysUserService) AopContext.currentProxy()).saveUsers(list, password));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUsers(List<SysUserExcelDto> list, String password) {
        ExcelUtils.unTransList(list);
        List<SysUser> sysUsers = SysUserConvert.INSTANCE.convertList(list);
        sysUsers.forEach(sysUser -> {
            sysUser.setId(IdGenerator.nextId());
            sysUser.setPassword(this.passwordEncoder.encode(password));
            sysUser.setSuperAdmin(false);
        });

        // 批量新增
        SysUserService proxy = (SysUserService) AopContext.currentProxy();
        proxy.saveBatch(sysUsers);
    }

    @Override
    public SysUserVo getByPhone(String phone) {
        SysUser sysUser = this.baseMapper.getByPhone(phone);
        return SysUserConvert.INSTANCE.convert(sysUser);
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
    public SysUser registerUser(SysUser sysUser) {
        // 校验是否已注册
        SysUser user = this.baseMapper.getByUsername(sysUser.getUsername());
        if (user != null) {
            throw new ServerException(UserResponseStatus.USER_REGISTER_EXISTS, sysUser.getUsername());
        }

        sysUser.setId(IdGenerator.nextId());
        sysUser.setSuperAdmin(false);
        // 密码加密
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword().trim()));

        // 添加默认角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setId(IdGenerator.nextId());
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(DataConstants.DEFAULT_ROLE_ID);
        this.sysUserRoleService.save(sysUserRole);

        // 保存用户
        int result = this.baseMapper.insert(sysUser);
        if (result == 1) {
            return sysUser;
        } else {
            return null;
        }
    }

    @Override
    public boolean checkUsernameUnique(SysUsernameDto sysUsernameDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysUser::getUsername, SysUser::getId)
                .eq(SysUser::getUsername, sysUsernameDto.getUsername());
        SysUser sysUser = this.baseMapper.selectOne(queryWrapper, false);

        // 修改时，同手机号同ID为手机号唯一
        return Objects.isNull(sysUser) || Objects.equals(sysUsernameDto.getId(), sysUser.getId());
    }

    @Override
    public boolean checkPhoneUnique(SysUserPhoneDto sysUserPhoneDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysUser::getPhone, SysUser::getId)
                .eq(SysUser::getPhone, sysUserPhoneDto.getPhone());
        SysUser sysUser = this.baseMapper.selectOne(queryWrapper, false);

        // 修改时，同手机号同ID为手机号唯一
        return Objects.isNull(sysUser) || Objects.equals(sysUserPhoneDto.getId(), sysUser.getId());
    }

    @Override
    public boolean checkEmailUnique(SysUserEmailDto sysUserEmailDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysUser::getEmail, SysUser::getId)
                .eq(SysUser::getEmail, sysUserEmailDto.getEmail());
        SysUser sysUser = this.baseMapper.selectOne(queryWrapper, false);

        // 修改时，同邮箱同ID为邮箱唯一
        return Objects.isNull(sysUser) || Objects.equals(sysUserEmailDto.getId(), sysUser.getId());
    }

    @Override
    public void checkUserAllowed(SysUser sysUser) {
        Long userId = LoginUserContext.currentUserId();
        SysUser user = this.baseMapper.selectById(userId);
        // 检查用户权限
        if (!user.getSuperAdmin() && sysUser.getSuperAdmin()) {
            throw new RuntimeException("不允许操作超级管理员用户");
        }
    }

    @Override
    public void resetPassword(SysResetPasswordDto sysResetPasswordDto) {
        SysUser sysUser = this.getById(sysResetPasswordDto.getId());
        checkUserAllowed(sysUser);
        sysUser.setPassword(passwordEncoder.encode(sysResetPasswordDto.getPassword()));
        this.updateById(sysUser);
    }

    @Override
    public void insertAuthRole(Long userId, List<Long> roleIds) {
        this.sysUserRoleService.saveOrUpdate(userId, roleIds);
    }

    @Override
    public void updateUserInfo(SysUserInfoDto sysUserInfoDto) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserInfoDto);
        // 设置登录用户ID
        sysUser.setId(LoginUserContext.currentUserId());

        // 校验手机号码
        if (StringUtils.isNotEmpty(sysUser.getPhone())) {
            SysUser user = this.baseMapper.getByPhone(sysUser.getPhone());
            if (Objects.nonNull(user) && !user.getId().equals(sysUser.getId())) {
                throw new ServerException(UserResponseStatus.USER_PROFILE_PHONE_EXISTS, sysUser.getPhone());
            }
        }

        // 校验邮箱
        if (StringUtils.isNotEmpty(sysUser.getEmail())) {
            SysUser user = this.baseMapper.getByEmail(sysUser.getEmail());
            if (Objects.nonNull(user) && !user.getId().equals(sysUser.getId())) {
                throw new ServerException(UserResponseStatus.USER_PROFILE_EMAIL_EXISTS, sysUser.getPhone());
            }
        }

        // 更新并更新用户登录信息
        if (this.updateById(sysUser)) {
            SysUser user = this.baseMapper.selectById(sysUser.getId());
            this.applicationEventPublisher.publishEvent(new RefreshTokenEvent(user));
            return;
        }

        throw new ServerException(UserResponseStatus.USER_PROFILE_ALTER_INFO_ERROR);
    }

    @Override
    public String updateAvatar(MultipartFile file) {
        log.info("上传头像文件：{}", file.getOriginalFilename());
        StorageVo storageVo = this.storageApi.upload(file, "avatar");
        if (storageVo == null || StringUtils.isEmpty(storageVo.getUrl())) {
            throw new ServerException(UserResponseStatus.USER_PROFILE_ALTER_AVATAR_ERROR);
        }
        SysUser sysUser = new SysUser();
        // 设置登录用户ID
        sysUser.setId(LoginUserContext.currentUserId());
        sysUser.setAvatar(storageVo.getUrl());

        // 更新并更新用户登录信息
        if (this.updateById(sysUser)) {
            SysUser user = this.baseMapper.selectById(sysUser.getId());
            this.applicationEventPublisher.publishEvent(new RefreshTokenEvent(user));
            return storageVo.getUrl();
        }

        throw new ServerException(UserResponseStatus.USER_PROFILE_ALTER_AVATAR_ERROR);
    }

    @Override
    public void updatePassword(SysUserPasswordDto passwordDto) {
        LoginUser loginUser = LoginUserContext.currentUser();
        assert loginUser != null;
        SysUser sysUser = this.baseMapper.selectById(loginUser.getId());

        // 校验密码
        if (!this.passwordEncoder.matches(passwordDto.getPassword(), sysUser.getPassword())) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_NOT_MATCH);
        }
        if (this.passwordEncoder.matches(passwordDto.getNewPassword(), sysUser.getPassword())) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_SAME_AS_OLD_ERROR);
        }

        sysUser.setPassword(this.passwordEncoder.encode(passwordDto.getNewPassword()));

        // 更新密码
        if (!this.updateById(sysUser)) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_CHANGE_ERROR);
        }
    }

    @Override
    public SysUserInfoVo getUserInfo(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }

        SysUser sysUser = this.baseMapper.selectById(userId);
        SysUserInfoVo sysUserInfoVo = BeanUtil.copyProperties(sysUser, SysUserInfoVo.class);

        // 机构名称
        CompletableFuture<String> orgNameFuture = CompletableFuture
                .supplyAsync(() -> this.sysOrgService.getOrgNameById(sysUser.getOrgId()), executor);

        // 用户岗位名称列表
        CompletableFuture<String> postNameListFuture = CompletableFuture.supplyAsync(() -> {
            List<Long> postIdList = this.sysUserPostService.getPostIdsByUserId(userId);
            List<String> postNameList = this.sysPostService.getPostNameList(postIdList);
            return String.join(",", postNameList);
        }, executor);

        // 用户角色名称列表
        List<Long> roleIdList = this.sysUserRoleService.getRoleIdsByUserId(userId);
        List<String> roleNameList = this.sysRoleService.getRoleNameList(roleIdList);
        if (sysUser.getSuperAdmin()) {
            roleNameList.add(DataConstants.SUPER_ADMIN_NAME);
        }
        sysUserInfoVo.setRoleNameList(String.join(",", roleNameList));

        CompletableFuture.allOf(orgNameFuture, postNameListFuture);

        try {
            sysUserInfoVo.setOrgName(orgNameFuture.get());
            sysUserInfoVo.setPostNameList(postNameListFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new ServerException(e.getMessage());
        }

        return sysUserInfoVo;
    }

}
