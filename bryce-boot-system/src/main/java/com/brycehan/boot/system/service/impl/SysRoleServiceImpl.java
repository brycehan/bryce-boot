package com.brycehan.boot.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.entity.PageResult;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.enums.DataScope;
import com.brycehan.boot.common.enums.YesNoType;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysRoleConvert;
import com.brycehan.boot.system.entity.dto.*;
import com.brycehan.boot.system.entity.po.SysRole;
import com.brycehan.boot.system.entity.vo.SysRoleVo;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysRoleDataScopeService;
import com.brycehan.boot.system.service.SysRoleMenuService;
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

    private final SysRoleDataScopeService sysRoleDataScopeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        sysRole.setId(IdGenerator.nextId());

        // 保存角色
        sysRole.setDataScope(DataScope.SELF);
        this.baseMapper.insert(sysRole);

        // 保存角色菜单关系
        this.sysRoleMenuService.saveOrUpdate(sysRole.getId(), sysRoleDto.getMenuIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);

        // 更新角色
        this.baseMapper.updateById(sysRole);

        // 更新角色菜单关系
        this.sysRoleMenuService.saveOrUpdate(sysRoleDto.getId(), sysRoleDto.getMenuIds());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull).toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 删除角色
        this.baseMapper.deleteByIds(ids);

        // 删除用户角色关系
        this.sysUserRoleService.deleteByRoleIds(ids);

        // 删除角色菜单关系
        this.sysRoleMenuService.deleteByRoleIds(ids);

        // 删除角色数据权限关系
        this.sysRoleDataScopeService.deleteByRoleIds(ids);
    }

    @Override
    public PageResult<SysRoleVo> page(SysRolePageDto sysRolePageDto) {
        IPage<SysRole> page = this.baseMapper.selectPage(sysRolePageDto.toPage(), getWrapper(sysRolePageDto));
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
        wrapper.eq(Objects.nonNull(sysRolePageDto.getOrgId()), SysRole::getOrgId, sysRolePageDto.getOrgId());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getName()), SysRole::getName, sysRolePageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getCode()), SysRole::getCode, sysRolePageDto.getCode());
        addTimeRangeCondition(wrapper, SysRole::getCreatedTime, sysRolePageDto.getCreatedTimeStart(), sysRolePageDto.getCreatedTimeEnd());

        // 数据权限
        dataScopeWrapper(wrapper);

        return wrapper;
    }

    @Override
    public void export(SysRolePageDto sysRolePageDto) {
        List<SysRole> sysRoleList = this.baseMapper.selectList(getWrapper(sysRolePageDto));
        List<SysRoleVo> sysRoleVoList = SysRoleConvert.INSTANCE.convert(sysRoleList);
        String today = DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN);
        ExcelUtils.export(SysRoleVo.class, "系统角色", "系统角色", sysRoleVoList);
    }

    @Override
    public List<SysRoleVo> list(SysRolePageDto sysRolePageDto) {
        List<SysRole> sysRoleList = this.baseMapper.selectList(getWrapper(sysRolePageDto));
        return SysRoleConvert.INSTANCE.convert(sysRoleList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void dataScope(SysRoleDataScopeDto dataScopeDto) {
        SysRole sysRole = this.baseMapper.selectById(dataScopeDto.getId());
        if (sysRole == null) {
            return;
        }
        // 更新角色
        sysRole.setDataScope(dataScopeDto.getDataScope());
        this.baseMapper.updateById(sysRole);

        // 更新角色数据范围关系
        if (dataScopeDto.getDataScope() == DataScope.CUSTOM) {
            this.sysRoleDataScopeService.saveOrUpdate(dataScopeDto.getId(), dataScopeDto.getOrgIds());
        } else {
            this.sysRoleDataScopeService.deleteByRoleIds(Collections.singletonList(dataScopeDto.getId()));
        }
    }

    @Override
    public List<String> getRoleNameList(List<Long> roleIdList) {
        if (CollectionUtils.isNotEmpty(roleIdList)) {
            return this.baseMapper.selectList(new LambdaQueryWrapper<SysRole>().in(SysRole::getId, roleIdList))
                    .stream().map(SysRole::getName).toList();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean checkCodeUnique(SysRoleCodeDto sysRoleCodeDto) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .select(SysRole::getCode, SysRole::getId)
                .eq(SysRole::getCode, sysRoleCodeDto.getCode());
        SysRole sysRole = this.baseMapper.selectOne(queryWrapper, false);

        // 修改时，同角色编码同ID为编码唯一
        return Objects.isNull(sysRole) || Objects.equals(sysRoleCodeDto.getId(), sysRole.getId());
    }

    @Override
    public PageResult<SysRoleVo> assignRolePage(SysAssignRolePageDto sysAssignRolePageDto) {
        List<Long> roleIds = this.sysUserRoleService.getRoleIdsByUserId(sysAssignRolePageDto.getUserId());
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysRole::getId, SysRole::getName, SysRole::getCode, SysRole::getCreatedTime);

        if (CollUtil.isEmpty(roleIds) && sysAssignRolePageDto.getAssigned() == YesNoType.YES) {
            return new PageResult<>(0, new ArrayList<>(0));
        }

        if (sysAssignRolePageDto.getAssigned() == YesNoType.YES) {
            queryWrapper.in(CollUtil.isNotEmpty(roleIds), SysRole::getId, roleIds);
        } else {
            queryWrapper.notIn(CollUtil.isNotEmpty(roleIds), SysRole::getId, roleIds);
        }

        IPage<SysRole> page = this.page(sysAssignRolePageDto.toPage(), queryWrapper);
        return new PageResult<>(page.getTotal(), SysRoleConvert.INSTANCE.convert(page.getRecords()));
    }

}
