package com.brycehan.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.api.system.StorageApi;
import com.brycehan.boot.api.system.vo.StorageVo;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.UserResponseStatus;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.constant.ParamConstants;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.enums.AccessType;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.enums.YesNoType;
import com.brycehan.boot.common.util.FileUploadUtils;
import com.brycehan.boot.common.util.MimeTypeUtils;
import com.brycehan.boot.common.util.ValidatorUtils;
import com.brycehan.boot.common.util.excel.ExcelUtils;
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
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

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

    private final SysParamService sysParamService;

    private final ExecutorService executor;

    private final StorageApi storageApi;

    private final Validator validator;

    @Override
    @Transactional
    public void save(SysUserDto sysUserDto) {
        sysRoleService.checkRoleDataScope(sysUserDto.getRoleIds().toArray(Long[]::new));
        sysOrgService.checkOrgDataScope(sysUserDto.getOrgId());
        // 判断用户名是否存在
        SysUser user = baseMapper.getByUsername(sysUserDto.getUsername());
        if (user != null) {
            throw new RuntimeException("账号已经存在");
        }

        // 判断手机号是否存在
        user = baseMapper.getByPhone(sysUserDto.getPhone());
        if (user != null) {
            throw new RuntimeException("手机号已经存在");
        }

        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserDto);

        sysUser.setPassword(passwordEncoder.encode(sysUserDto.getPassword()));
        sysUser.setId(IdGenerator.nextId());

        // 保存用户
        baseMapper.insert(sysUser);

        // 保存用户角色关系
        sysUserRoleService.saveOrUpdate(sysUser.getId(), sysUserDto.getRoleIds());

        // 保存用户岗位关系
        sysUserPostService.saveOrUpdate(sysUser.getId(), sysUserDto.getPostIds());
    }

    @Override
    @Transactional
    public void update(SysUserDto sysUserDto) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserDto);
        checkUserAllowed(sysUser);
        checkUserDataScope(sysUser);
        sysRoleService.checkRoleDataScope(sysUserDto.getRoleIds().toArray(Long[]::new));
        sysOrgService.checkOrgDataScope(sysUserDto.getOrgId());

        // 判断手机号是否存在
        if (StrUtil.isNotBlank(sysUserDto.getPhone())) {
            SysUser user = baseMapper.getByPhone(sysUserDto.getPhone());
            if (user != null && !user.getId().equals(sysUserDto.getId())) {
                throw new RuntimeException("手机号已经存在");
            }
        }

        // 如果密码不为空，则进行加密处理
        if (StrUtil.isBlank(sysUser.getPassword())) {
            sysUser.setPassword(null);
        } else {
            sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
        }

        // 更新用户
        baseMapper.updateById(sysUser);

        // 机构为空时，清空机构ID
        if (sysUser.getOrgId() == null) {
            LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(SysUser::getOrgId, null);
            updateWrapper.eq(SysUser::getId, sysUser.getId());
            update(updateWrapper);
        }

        // 更新用户角色关系
        sysUserRoleService.saveOrUpdate(sysUserDto.getId(), sysUserDto.getRoleIds());

        // 更新用户岗位关系
        sysUserPostService.saveOrUpdate(sysUserDto.getId(), sysUserDto.getPostIds());
    }

    @Override
    @Transactional
    public void delete(IdsDto idsDto) {
        for (Long userId : idsDto.getIds()) {
            SysUser sysUser = SysUser.of(userId);
            checkUserAllowed(sysUser);
            checkUserDataScope(sysUser);
        }

        // 删除用户角色关系
        sysUserRoleService.deleteByUserIds(idsDto.getIds());

        // 删除用户岗位关系
        sysUserPostService.deleteByUserIds(idsDto.getIds());

        // 删除用户
        baseMapper.deleteByIds(idsDto.getIds());
    }

    @Override
    public SysUserVo get(Long id) {
        checkUserDataScope(SysUser.of(id));
        SysUser sysUser = getById(id);
        SysUserVo sysUserVo = SysUserConvert.INSTANCE.convert(sysUser);

        // 机构名称
        sysUserVo.setOrgName(sysOrgService.getOrgNameById(sysUser.getOrgId()));

        // 用户角色Ids
        List<Long> roleIds = sysUserRoleService.getRoleIdsByUserId(id);
        sysUserVo.setRoleIds(roleIds);

        // 用户岗位Ids
        List<Long> postIds = sysUserPostService.getPostIdsByUserId(id);
        sysUserVo.setPostIds(postIds);

        return sysUserVo;
    }

    @Override
    public PageResult<SysUserVo> page(SysUserPageDto sysUserPageDto) {
        // 查询参数
        Map<String, Object> params = getParams(sysUserPageDto);

        // 分页查询
        IPage<SysUser> page = sysUserPageDto.toPage();
        params.put(DataConstants.PAGE, page);

        // 数据列表
        List<SysUser> list = baseMapper.list(params);
        List<SysUserVo> sysUserVoList = SysUserConvert.INSTANCE.convert(list);

        // 处理机构名称
        Map<Long, String> orgNames = sysOrgService.getOrgNamesByIds(list.stream().map(SysUser::getOrgId).toList());
        sysUserVoList.forEach(sysUserVo -> sysUserVo.setOrgName(orgNames.get(sysUserVo.getOrgId())));

        return new PageResult<>(page.getTotal(), sysUserVoList);
    }

    @Override
    public List<SysUserVo> list(SysUserPageDto sysUserPageDto) {
        List<SysUser> sysUserList = baseMapper.selectList(getWrapper(sysUserPageDto));
        return SysUserConvert.INSTANCE.convert(sysUserList);
    }

    /**
     * 封装查询参数
     *
     * @param sysUserPageDto 系统用户分页dto
     * @return 查询参数
     */
    private Map<String, Object> getParams(SysUserPageDto sysUserPageDto) {
        Map<String, Object> params = BeanUtil.beanToMap(sysUserPageDto, false, false);
        // 数据权限过滤
        params.put(DataConstants.DATA_SCOPE, getDataScope("su", null));
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
        wrapper.eq(sysUserPageDto.getGender() != null, SysUser::getGender, sysUserPageDto.getGender());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getType()), SysUser::getType, sysUserPageDto.getType());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getOrgId()), SysUser::getOrgId, sysUserPageDto.getOrgId());
        wrapper.eq(Objects.nonNull(sysUserPageDto.getStatus()), SysUser::getStatus, sysUserPageDto.getStatus());
        addTimeRangeCondition(wrapper, SysUser::getCreatedTime, sysUserPageDto.getCreatedTimeStart(), sysUserPageDto.getCreatedTimeEnd());

        // 数据权限过滤
        dataScopeWrapper(wrapper);

        return wrapper;
    }

    @Override
    public void export(SysUserPageDto sysUserPageDto) {
        List<SysUser> sysUserList = baseMapper.selectList(getWrapper(sysUserPageDto));
        List<SysUserVo> sysUserVoList = SysUserConvert.INSTANCE.convert(sysUserList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysUserVo.class, "系统用户_".concat(today), "系统用户", sysUserVoList);
    }

    @Override
    public String importByExcel(MultipartFile file, boolean isUpdateSupport) {
        if (file.isEmpty()) {
            throw new RuntimeException("请选择需要导入的用户数据文件");
        }
        // 验证文件格式
        FileUploadUtils.assertAllowed(file, MimeTypeUtils.EXCEL_EXTENSION);

        List<SysUserExcelDto> sysUserExcelDtoList = ExcelUtils.read(file, SysUserExcelDto.class);
        return saveUsers(sysUserExcelDtoList, isUpdateSupport);
    }

    @Override
    public String saveUsers(List<SysUserExcelDto> list, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(list)) {
            throw new RuntimeException("导入用户数据不能为空");
        }

        // 初始密码
        String password = sysParamService.getString(ParamConstants.SYSTEM_USER_INIT_PASSWORD);
        String encodedPassword = passwordEncoder.encode(password);

        // 批量处理
        int successNum = 0;
        int failNum = 0;
        StringBuilder sucessMessage = new StringBuilder();
        StringBuilder failMessage = new StringBuilder();
        for (SysUserExcelDto sysUserExcelDto : list) {
            String username = StringUtils.isBlank(sysUserExcelDto.getUsername()) ? "" : sysUserExcelDto.getUsername();
            try {
                SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserExcelDto);
                // 查询用户是否存在
                LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.select(SysUser::getId);
                queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
                SysUser user = getOne(queryWrapper, false);
                // 系统不存在用户时
                if (user == null) {
                    ValidatorUtils.validate(validator, sysUserExcelDto);
                    sysOrgService.checkOrgDataScope(sysUser.getOrgId());
                    sysUser.setId(IdGenerator.nextId());
                    sysUser.setPassword(encodedPassword);
                    baseMapper.insert(sysUser);
                    successNum++;
                    sucessMessage.append("<br/>").append(successNum).append("、账号 ").append(username).append(" 导入成功");
                } else if (isUpdateSupport) {
                    ValidatorUtils.validate(validator, sysUserExcelDto);
                    sysUser.setId(user.getId());
                    checkUserAllowed(sysUser);
                    checkUserDataScope(sysUser);
                    sysOrgService.checkOrgDataScope(sysUser.getOrgId());
                    baseMapper.updateById(sysUser);
                    successNum++;
                    sucessMessage.append("<br/>").append(successNum).append("、账号 ").append(username).append(" 更新成功");
                } else {
                    failNum++;
                    failMessage.append("<br/>").append(failNum).append("、账号 ").append(username).append(" 已存在");
                }
            } catch (Exception e) {
                failNum++;
                String message = "<br/>" + failNum + "、账号 " + username + " 导入失败：";
                failMessage.append(message).append(e.getMessage());
                log.error(message, e);
            }
        }

        if (failNum > 0) {
            throw new RuntimeException("很抱歉，导入失败！共 " + failNum + " 条数据格式不正确，错误如下：" + failMessage);
        }

        return "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：" + sucessMessage;
    }

    @Override
    @Transactional
    public SysUser registerUser(SysUser sysUser) {
        // 校验是否已注册
        SysUser user = baseMapper.getByUsername(sysUser.getUsername());
        if (user != null) {
            throw new ServerException(UserResponseStatus.USER_REGISTER_EXISTS, sysUser.getUsername());
        }

        sysUser.setId(IdGenerator.nextId());
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword().trim()));

        // 添加默认角色
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setId(IdGenerator.nextId());
        sysUserRole.setUserId(sysUser.getId());
        sysUserRole.setRoleId(DataConstants.DEFAULT_ROLE_ID);
        sysUserRoleService.save(sysUserRole);

        // 保存用户
        int result = baseMapper.insert(sysUser);
        if (result == 1) {
            return sysUser;
        } else {
            return null;
        }
    }

    @Override
    public void updateStatus(Long id, StatusType status) {
        SysUser sysUser = SysUser.of(id);

        // 不允许操作超级管理员状态
        checkUserAllowed(sysUser);
        checkUserDataScope(sysUser);

        sysUser.setStatus(status);
        updateById(sysUser);
    }

    @Override
    public void updateUserInfo(SysUserInfoDto sysUserInfoDto) {
        SysUser sysUser = SysUserConvert.INSTANCE.convert(sysUserInfoDto);
        // 设置登录用户ID
        sysUser.setId(LoginUserContext.currentUserId());

        // 校验手机号码
        if (StringUtils.isNotEmpty(sysUser.getPhone())) {
            SysUser user = baseMapper.getByPhone(sysUser.getPhone());
            if (Objects.nonNull(user) && !user.getId().equals(sysUser.getId())) {
                throw new ServerException(UserResponseStatus.USER_PROFILE_PHONE_EXISTS, sysUser.getPhone());
            }
        }

        // 校验邮箱
        if (StringUtils.isNotEmpty(sysUser.getEmail())) {
            SysUser user = baseMapper.getByEmail(sysUser.getEmail());
            if (Objects.nonNull(user) && !user.getId().equals(sysUser.getId())) {
                throw new ServerException(UserResponseStatus.USER_PROFILE_EMAIL_EXISTS, sysUser.getPhone());
            }
        }

        // 更新并更新用户登录信息
        if (updateById(sysUser)) {
            SysUser user = baseMapper.selectById(sysUser.getId());
            applicationEventPublisher.publishEvent(new RefreshTokenEvent(user));
            return;
        }

        throw new ServerException(UserResponseStatus.USER_PROFILE_ALTER_INFO_ERROR);
    }

    @Override
    public String updateAvatar(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("用户头像不能为空");
        }
        if (log.isDebugEnabled()) {
            log.debug("上传头像文件：{}", file.getOriginalFilename());
        }

        // 上传头像文件
        StorageVo storageVo = storageApi.upload(file, AccessType.PUBLIC, MimeTypeUtils.IMAGE_EXTENSION);

        if (storageVo == null || StringUtils.isEmpty(storageVo.getUrl())) {
            throw new ServerException(UserResponseStatus.USER_PROFILE_ALTER_AVATAR_ERROR);
        }

        SysUser sysUser = new SysUser();
        // 设置登录用户ID
        sysUser.setId(LoginUserContext.currentUserId());
        sysUser.setAvatar(storageVo.getUrl());

        // 更新并更新用户登录信息
        if (updateById(sysUser)) {
            SysUser user = baseMapper.selectById(sysUser.getId());
            applicationEventPublisher.publishEvent(new RefreshTokenEvent(user));
            return storageVo.getUrl();
        }

        throw new ServerException(UserResponseStatus.USER_PROFILE_ALTER_AVATAR_ERROR);
    }

    @Override
    public void updatePassword(SysUserPasswordDto passwordDto) {
        LoginUser loginUser = LoginUserContext.currentUser();
        assert loginUser != null;
        SysUser sysUser = baseMapper.selectById(loginUser.getId());

        // 校验密码
        if (!passwordEncoder.matches(passwordDto.getPassword(), sysUser.getPassword())) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_NOT_MATCH);
        }
        if (passwordEncoder.matches(passwordDto.getNewPassword(), sysUser.getPassword())) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_SAME_AS_OLD_ERROR);
        }

        sysUser.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));

        // 更新密码
        if (!updateById(sysUser)) {
            throw new ServerException(UserResponseStatus.USER_PASSWORD_CHANGE_ERROR);
        }
    }

    @Override
    public void resetPassword(SysResetPasswordDto sysResetPasswordDto) {
        SysUser sysUser = getById(sysResetPasswordDto.getId());
        checkUserAllowed(sysUser);
        checkUserDataScope(sysUser);
        sysUser.setPassword(passwordEncoder.encode(sysResetPasswordDto.getPassword()));
        updateById(sysUser);
    }

    @Override
    public SysUser getByUsername(String username) {
        return baseMapper.getByUsername(username);
    }

    @Override
    public SysUser getByPhone(String phone) {
        return baseMapper.getByPhone(phone);
    }

    @Override
    public SysUserInfoVo getUserInfo(Long userId) {
        if (Objects.isNull(userId)) {
            return null;
        }

        SysUser sysUser = baseMapper.selectById(userId);
        SysUserInfoVo sysUserInfoVo = BeanUtil.copyProperties(sysUser, SysUserInfoVo.class);

        // 机构名称
        CompletableFuture<String> orgNameFuture = CompletableFuture
                .supplyAsync(() -> sysOrgService.getOrgNameById(sysUser.getOrgId()), executor);

        // 用户岗位名称列表
        CompletableFuture<String> postNameListFuture = CompletableFuture.supplyAsync(() -> {
            List<Long> postIdList = sysUserPostService.getPostIdsByUserId(userId);
            List<String> postNameList = sysPostService.getPostNameList(postIdList);
            return String.join(",", postNameList);
        }, executor);

        // 用户角色名称列表
        List<Long> roleIdList = sysUserRoleService.getRoleIdsByUserId(userId);
        List<String> roleNameList = sysRoleService.getRoleNameList(roleIdList);
        if (sysUser.isSuperAdmin()) {
            roleNameList = new ArrayList<>(roleNameList);
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

    @Override
    public PageResult<SysUserVo> assignUserPage(SysAssignUserPageDto sysAssignUserPageDto) {
        // 指定角色已分配的用户ID列表
        List<Long> userIds = sysUserRoleService.getUserIdsByRoleId(sysAssignUserPageDto.getRoleId());

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysUser::getId, SysUser::getUsername, SysUser::getNickname, SysUser::getPhone, SysUser::getStatus, SysUser::getCreatedTime);

        if (CollUtil.isEmpty(userIds) && sysAssignUserPageDto.getAssigned() == YesNoType.YES) {
            return new PageResult<>(0, new ArrayList<>(0));
        }

        // 已分配/未分配 条件过滤
        if (sysAssignUserPageDto.getAssigned() == YesNoType.YES) {
            queryWrapper.in(CollUtil.isNotEmpty(userIds), SysUser::getId, userIds);
        } else {
            queryWrapper.notIn(CollUtil.isNotEmpty(userIds), SysUser::getId, userIds);
        }

        // 数据权限过滤
        dataScopeWrapper(queryWrapper);

        // 分页查询
        IPage<SysUser> page = page(sysAssignUserPageDto.toPage(), queryWrapper);
        return new PageResult<>(page.getTotal(), SysUserConvert.INSTANCE.convert(page.getRecords()));
    }

    @Override
    public List<SysUser> getUserListByOrgIds(Collection<Long> orgIds) {
        if (CollUtil.isEmpty(orgIds)) {
            return  List.of();
        }

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysUser::getOrgId, orgIds);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public void checkUserAllowed(SysUser sysUser) {
        if (sysUser == null) {
            return;
        }
        if (sysUser.getId() != null && sysUser.isSuperAdmin()) {
            throw new RuntimeException("不允许操作超级管理员用户");
        }
    }

    @Override
    public void checkUserDataScope(SysUser sysUser) {
        if (sysUser.getId() == null || SysUser.isSuperAdmin(sysUser)) {
            return;
        }

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getId, sysUser.getId());

        dataScopeWrapper(queryWrapper);
        if (!baseMapper.exists(queryWrapper)) {
            throw new RuntimeException("没有权限访问用户数据");
        }
    }

    @Override
    public boolean checkUsernameUnique(SysUsernameDto sysUsernameDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysUser::getUsername, SysUser::getId)
                .eq(SysUser::getUsername, sysUsernameDto.getUsername());
        SysUser sysUser = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同手机号同ID为手机号唯一
        return Objects.isNull(sysUser) || Objects.equals(sysUsernameDto.getId(), sysUser.getId());
    }

    @Override
    public boolean checkPhoneUnique(SysUserPhoneDto sysUserPhoneDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysUser::getPhone, SysUser::getId)
                .eq(SysUser::getPhone, sysUserPhoneDto.getPhone());
        SysUser sysUser = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同手机号同ID为手机号唯一
        return Objects.isNull(sysUser) || Objects.equals(sysUserPhoneDto.getId(), sysUser.getId());
    }

    @Override
    public boolean checkEmailUnique(SysUserEmailDto sysUserEmailDto) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysUser::getEmail, SysUser::getId)
                .eq(SysUser::getEmail, sysUserEmailDto.getEmail());
        SysUser sysUser = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同邮箱同ID为邮箱唯一
        return Objects.isNull(sysUser) || Objects.equals(sysUserEmailDto.getId(), sysUser.getId());
    }
}
