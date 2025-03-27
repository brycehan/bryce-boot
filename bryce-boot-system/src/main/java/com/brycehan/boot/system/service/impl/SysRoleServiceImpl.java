package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.enums.DataScopeType;
import com.brycehan.boot.common.enums.StatusType;
import com.brycehan.boot.common.enums.YesNoType;
import com.brycehan.boot.common.util.excel.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysRoleConvert;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.vo.SysRoleVo;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysRoleMenuService;
import com.brycehan.boot.system.service.SysRoleOrgService;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统角色表服务实现类
 *
 * @since 2023/08/24
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysUserRoleService sysUserRoleService;

    private final SysRoleMenuService sysRoleMenuService;

    private final SysRoleOrgService sysRoleOrgService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        sysRole.setId(IdGenerator.nextId());

        // 保存角色
        sysRole.setDataScope(DataScopeType.ALL);
        baseMapper.insert(sysRole);

        // 保存角色菜单关系
        sysRoleMenuService.saveOrUpdate(sysRole.getId(), sysRoleDto.getMenuIds());
    }

    @Override
    @Transactional
    public void update(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        checkRoleAllowed(sysRole);
        checkRoleDataScope(sysRole.getId());

        // 更新角色
        baseMapper.updateById(sysRole);

        // 更新角色菜单关系
        sysRoleMenuService.saveOrUpdate(sysRoleDto.getId(), sysRoleDto.getMenuIds());
    }

    @Override
    @Transactional
    public void delete(IdsDto idsDto) {
        for (Long id : idsDto.getIds()) {
            checkRoleAllowed(SysRole.of(id));
            checkRoleDataScope(id);
            if (sysUserRoleService.countUserRoleByRoleId(id) > 0) {
                String roleName = lambdaQuery().select(SysRole::getName).eq(SysRole::getId, id).oneOpt()
                        .map(SysRole::getName).orElse("");
                throw new IllegalArgumentException(StrUtil.format("角色“{}”已分配用户，不允许删除", roleName));
            }
        }

        // 删除角色
        baseMapper.deleteByIds(idsDto.getIds());

        // 删除用户角色关系
        sysUserRoleService.deleteByRoleIds(idsDto.getIds());

        // 删除角色菜单关系
        sysRoleMenuService.deleteByRoleIds(idsDto.getIds());

        // 删除角色数据权限关系
        sysRoleOrgService.deleteByRoleIds(idsDto.getIds());
    }

    @Override
    public PageResult<SysRoleVo> page(SysRolePageDto sysRolePageDto) {
        IPage<SysRole> page = baseMapper.selectPage(sysRolePageDto.toPage(), getWrapper(sysRolePageDto));
        return new PageResult<>(page.getTotal(), SysRoleConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysRolePageDto 系统角色分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysRole> getWrapper(SysRolePageDto sysRolePageDto) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysRolePageDto.getStatus()), SysRole::getStatus, sysRolePageDto.getStatus());
        wrapper.eq(Objects.nonNull(sysRolePageDto.getDeptId()), SysRole::getDeptId, sysRolePageDto.getDeptId());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getName()), SysRole::getName, sysRolePageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getCode()), SysRole::getCode, sysRolePageDto.getCode());
        addTimeRangeCondition(wrapper, SysRole::getCreatedTime, sysRolePageDto.getCreatedTimeStart(), sysRolePageDto.getCreatedTimeEnd());

        // 数据权限过滤
        dataScopeWrapper(wrapper);

        return wrapper;
    }

    @Override
    public void export(SysRolePageDto sysRolePageDto) {
        List<SysRole> sysRoleList = baseMapper.selectList(getWrapper(sysRolePageDto));
        List<SysRoleVo> sysRoleVoList = SysRoleConvert.INSTANCE.convert(sysRoleList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysRoleVo.class, "角色数据_"+ today, "角色数据", sysRoleVoList);
    }

    @Override
    public void updateStatus(Long id, StatusType status) {
        // 不允许操作超级管理员状态
        checkRoleAllowed(SysRole.of(id));
        checkRoleDataScope(id);

        // 更新状态
        LambdaUpdateWrapper<SysRole> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysRole::getStatus, status).eq(SysRole::getId, id);
        update(updateWrapper);
    }

    @Override
    public List<String> getRoleNameList(List<Long> roleIdList) {
        if (CollectionUtils.isNotEmpty(roleIdList)) {
            return baseMapper.selectList(new LambdaQueryWrapper<SysRole>().in(SysRole::getId, roleIdList))
                    .stream().map(SysRole::getName).toList();
        }
        return new ArrayList<>();
    }

    @Override
    public Set<SysRole> getRoleByUserId(Long userId) {
        return baseMapper.getRoleByUserId(userId);
    }

    @Override
    public List<SysRoleVo> list(SysRolePageDto sysRolePageDto) {
        List<SysRole> sysRoleList = baseMapper.selectList(getWrapper(sysRolePageDto));
        return SysRoleConvert.INSTANCE.convert(sysRoleList);
    }

    @Override
    @Transactional
    public void assignDataScope(SysRoleOrgDto sysRoleOrgDto) {
        SysRole sysRole = baseMapper.selectById(sysRoleOrgDto.getId());
        if (sysRole == null) {
            return;
        }
        checkRoleAllowed(sysRole);
        checkRoleDataScope(sysRoleOrgDto.getId());
        // 更新角色
        sysRole.setDataScope(sysRoleOrgDto.getDataScope());
        baseMapper.updateById(sysRole);

        // 更新角色数据范围关系
        if (sysRoleOrgDto.getDataScope() == DataScopeType.CUSTOM) {
            sysRoleOrgService.saveOrUpdate(sysRoleOrgDto.getId(), sysRoleOrgDto.getDeptIds());
        } else {
            sysRoleOrgService.deleteByRoleIds(Collections.singletonList(sysRoleOrgDto.getId()));
        }
    }

    @Override
    public PageResult<SysRoleVo> assignRolePage(SysAssignRolePageDto sysAssignRolePageDto) {
        // 指定用户已分配的角色ID列表
        List<Long> roleIds = sysUserRoleService.getRoleIdsByUserId(sysAssignRolePageDto.getUserId());

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysRole::getId, SysRole::getName, SysRole::getCode, SysRole::getCreatedTime);
        queryWrapper.ne(SysRole::getId, DataConstants.ROLE_SUPER_ADMIN_ID);

        if (CollUtil.isEmpty(roleIds) && sysAssignRolePageDto.getAssigned() == YesNoType.YES) {
            return new PageResult<>(0, new ArrayList<>(0));
        }

        // 已分配/未分配 条件过滤
        if (sysAssignRolePageDto.getAssigned() == YesNoType.YES) {
            queryWrapper.in(CollUtil.isNotEmpty(roleIds), SysRole::getId, roleIds);
        } else {
            queryWrapper.notIn(CollUtil.isNotEmpty(roleIds), SysRole::getId, roleIds);
            // 数据权限过滤
            dataScopeWrapper(queryWrapper);
        }

        // 分页查询
        IPage<SysRole> page = page(sysAssignRolePageDto.toPage(), queryWrapper);
        return new PageResult<>(page.getTotal(), SysRoleConvert.INSTANCE.convert(page.getRecords()));
    }

    @Override
    public void checkRoleAllowed(SysRole sysRole) {
        if (sysRole != null && sysRole.isSuperAdmin()) {
            throw new RuntimeException("不允许操作超级管理员角色");
        }
    }

    @Override
    public void checkRoleDataScope(Long... roleIds) {
        if (ArrayUtil.isEmpty(roleIds) || LoginUser.isSuperAdmin(LoginUserContext.currentUser())) {
            return;
        }

        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysRole::getId);
        if (roleIds.length == 1) {
            queryWrapper.eq(SysRole::getId, roleIds[0]);
        } else {
            queryWrapper.in(SysRole::getId, Arrays.asList(roleIds));
        }

        // 数据权限过滤
        dataScopeWrapper(queryWrapper);

        List<Long> roleIdList = listObjs(queryWrapper, Convert::toLong);
        for (Long roleId : roleIds) {
            if (!roleIdList.contains(roleId)) {
                throw new RuntimeException("没有权限访问角色数据");
            }
        }
    }

    @Override
    public boolean checkRoleCodeUnique(SysRoleCodeDto sysRoleCodeDto) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysRole::getId)
                .eq(SysRole::getCode, sysRoleCodeDto.getCode());
        SysRole sysRole = baseMapper.selectOne(queryWrapper, false);

        // 修改时，同角色编码同ID为编码唯一
        return Objects.isNull(sysRole) || Objects.equals(sysRoleCodeDto.getId(), sysRole.getId());
    }
}
