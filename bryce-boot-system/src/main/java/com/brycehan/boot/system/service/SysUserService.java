package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.framework.mybatis.service.BaseService;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.vo.SysUserInfoVo;
import com.brycehan.boot.system.entity.vo.SysUserVo;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户服务
 *
 * @since 2022/5/08
 * @author Bryce Han
 */
public interface SysUserService extends BaseService<SysUser> {


    /**
     * 添加系统用户
     *
     * @param sysUserDto 系统用户Dto
     */
    void save(SysUserDto sysUserDto);

    /**
     * 更新系统用户
     *
     * @param sysUserDto 系统用户Dto
     */
    void update(SysUserDto sysUserDto);

    /**
     * 根据ID查询系统用户
     *
     * @param id ID
     * @return 系统用户
     */
    SysUserVo get(Long id);

    /**
     * 系统用户分页查询
     *
     * @param sysUserPageDto 查询条件
     * @return 分页信息
     */
    PageResult<SysUserVo> page(SysUserPageDto sysUserPageDto);

    /**
     * 系统用户列表查询
     *
     * @param sysUserPageDto 查询条件
     * @return 用户列表
     */
    List<SysUserVo> list(SysUserPageDto sysUserPageDto);

    /**
     * 系统用户导出数据
     *
     * @param sysUserPageDto 系统用户查询条件
     */
    void export(SysUserPageDto sysUserPageDto);

    /**
     * 批量导入用户
     *
     * @param file Excel 文件
     * @param isUpdateSupport 是否更新已经存在的用户数据
     */
    String importByExcel(MultipartFile file, boolean isUpdateSupport);

    /**
     * 批量导入保存用户
     *
     * @param list      用户列表
     * @param isUpdateSupport 是否更新已经存在的用户数据
     */
    String saveUsers(List<SysUserExcelDto> list, boolean isUpdateSupport);

    /**
     * 注册用户
     *
     * @param sysUser 用户
     */
    SysUser registerUser(SysUser sysUser);

    /**
     * 更新系统用户状态
     *
     * @param id 系统用户ID
     * @param status 用户状态
     */
    void updateStatus(Long id, StatusType status);

    /**
     * 更新个人信息
     *
     * @param sysUserInfoDto 个人信息
     */
    void updateUserInfo(SysUserInfoDto sysUserInfoDto);

    /**
     * 更新用户头像
     *
     * @param file 用户头像信息
     */
    String updateAvatar(MultipartFile file);

    /**
     * 更新密码
     *
     * @param passwordDto 系统用户密码Dto
     */
    void updatePassword(SysUserPasswordDto passwordDto);

    /**
     * 重置密码
     *
     * @param sysResetPasswordDto 要重置的用户
     */
    void resetPassword(SysResetPasswordDto sysResetPasswordDto);

    /**
     * 根据账号查询用户
     *
     * @param username 账号
     * @return 系统用户
     */
    SysUser getByUsername(String username);

    /**
     * 根据手机号码查询用户
     *
     * @param phone 手机号码
     * @return 系统用户
     */
    SysUser getByPhone(String phone);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserInfoVo getUserInfo(Long userId);

    /**
     * 根据用户ID查询 用户ID用户名称列表map
     *
     * @param userIds 用户ID列表
     * @return 用户ID用户名称列表map
     */
    default Map<Long, String> getUsernamesByIds(List<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysUser::getId, SysUser::getUsername);
        queryWrapper.in(SysUser::getId, userIds);

        List<SysUser> sysUserList = getBaseMapper().selectList(queryWrapper);

        if (CollectionUtils.isEmpty(sysUserList)) {
            return new HashMap<>();
        }

        return sysUserList.stream().collect(Collectors.toMap(SysUser::getId, SysUser::getUsername));
    }

    /**
     * 分配/未分配 给角色的用户分页查询
     *
     * @param pageDto 查询条件
     * @return 用户分页信息
     */
    PageResult<SysUserVo> assignUserPage(SysAssignUserPageDto pageDto);

    /**
     * 获得指定部门的用户数组
     *
     * @param orgIds 部门数组
     * @return 用户数组
     */
    List<SysUser> getUserListByOrgIds(Collection<Long> orgIds);

    /**
     * 校验用户是否允许操作（admin放行，普通用户不能操作admin账号）
     *
     * @param sysUser 系统用户信息
     */
    void checkUserAllowed(SysUser sysUser);

    /**
     * 校验用户数据权限
     *
     * @param sysUser 系统用户信息
     */
    void checkUserDataScope(SysUser sysUser);

    /**
     * 校验用户账号是否唯一
     *
     * @param sysUsernameDto 用户账号Dto
     * @return 结果，true唯一
     */
    boolean checkUsernameUnique(SysUsernameDto sysUsernameDto);

    /**
     * 校验用户手机号码是否唯一
     *
     * @param sysUserPhoneDto 手机号码Dto
     * @return 结果，true唯一
     */
    boolean checkPhoneUnique(SysUserPhoneDto sysUserPhoneDto);

    /**
     * 校验用户邮箱是否唯一
     *
     * @param sysUserEmailDto 用户邮箱Dto
     * @return 结果，true唯一
     */
    boolean checkEmailUnique(SysUserEmailDto sysUserEmailDto);
}
