package com.brycehan.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.http.UserResponseStatus;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.constant.UserConstants;
import com.brycehan.boot.common.util.DateTimeUtils;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.framework.security.JwtTokenProvider;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import com.brycehan.boot.system.convert.SysUserConvert;
import com.brycehan.boot.system.dto.*;
import com.brycehan.boot.system.entity.SysUser;
import com.brycehan.boot.system.entity.SysUserRole;
import com.brycehan.boot.system.mapper.SysUserMapper;
import com.brycehan.boot.system.service.SysUserPostService;
import com.brycehan.boot.system.service.SysUserRoleService;
import com.brycehan.boot.system.service.SysUserService;
import com.brycehan.boot.system.vo.SysUserVo;
import com.fhs.trans.service.impl.TransService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final TransService transService;

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
        // 查询参数
        Map<String, Object> params = getParams(sysUserPageDto);

        // 分页查询
        IPage<SysUser> page = getPage(sysUserPageDto);
        params.put(DataConstants.PAGE, page);

        // 数据列表
        List<SysUser> list = this.baseMapper.list(params);

        return new PageResult<>(page.getTotal(), SysUserConvert.INSTANCE.convert(list));
    }

    private Map<String, Object> getParams(SysUserPageDto sysUserPageDto) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", sysUserPageDto.getUsername());
        params.put("phone", sysUserPageDto.getPhone());
        params.put("gender", sysUserPageDto.getGender());
        params.put("type", sysUserPageDto.getType());
        params.put("orgId", sysUserPageDto.getOrgId());
        params.put("status", sysUserPageDto.getStatus());
        params.put("tenantId", sysUserPageDto.getTenantId());
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
        this.transService.transBatch(sysUserVoList);
        ExcelUtils.export(SysUserVo.class, "系统用户_".concat(DateTimeUtils.today()), "系统用户", sysUserVoList);
    }

    @Transactional
    @Override
    public void importByExcel(MultipartFile file, String password) {
        ExcelUtils.read(file, SysUserExcelDto.class, list -> saveUser(list, password));
    }

    private void saveUser(List<SysUserExcelDto> list, String password) {

        ExcelUtils.unTransList(list);
        List<SysUser> sysUsers = SysUserConvert.INSTANCE.convertList(list);
        sysUsers.forEach(sysUser -> {
            sysUser.setId(IdGenerator.nextId());
            sysUser.setPassword(this.passwordEncoder.encode(password));
        });

        saveBatch(sysUsers);
    }

    @Override
    public SysUserVo getByPhone(String phone) {
        SysUser sysUser = this.baseMapper.getByPhone(phone);
        return SysUserConvert.INSTANCE.convert(sysUser);
    }

    @Override
    public void updatePassword(SysUserPasswordDto passwordDto) {
        LoginUser loginUser = LoginUserContext.currentUser();
        // 校验密码
        assert loginUser != null;
        if (!this.passwordEncoder.matches(passwordDto.getPassword(), loginUser.getPassword())) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_NOT_MATCH);
        }
        if (this.passwordEncoder.matches(passwordDto.getNewPassword(), loginUser.getPassword())) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_SAME_AS_OLD_ERROR);
        }

        // 更新密码
        SysUser sysUser = new SysUser();
        sysUser.setId(loginUser.getId());
        sysUser.setPassword(this.passwordEncoder.encode(passwordDto.getNewPassword()));
        if (this.updateById(sysUser)) {
            // 更新缓存用户信息
            loginUser.setPassword(sysUser.getPassword());
            this.jwtTokenProvider.setLoginUser(loginUser);
        } else {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_CHANGE_ERROR);
        }
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
        sysUser.setId(IdGenerator.nextId());

        // 添加默认角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(DataConstants.DEFAULT_ROLE_ID);
        this.sysUserRoleService.save(sysUserRole);

        // 保存用户
        int result = this.baseMapper.insert(sysUser);
        if (result != 1) {
            throw new ServerException(UserResponseStatus.USER_REGISTER_ERROR);
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

}
