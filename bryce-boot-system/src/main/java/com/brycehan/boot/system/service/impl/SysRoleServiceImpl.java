package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.dto.IdsDto;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.common.enums.DataScopeType;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysRoleConvert;
import com.brycehan.boot.system.dto.SysRoleDataScopeDto;
import com.brycehan.boot.system.dto.SysRoleDto;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysRoleDataScopeService;
import com.brycehan.boot.system.service.SysRoleMenuService;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.service.SysUserRoleService;
import com.brycehan.boot.system.vo.SysRoleVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    public void save(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        sysRole.setId(IdGenerator.nextId());

        // 保存角色
        sysRole.setDataScope(DataScopeType.SELF.value());
        this.baseMapper.insert(sysRole);

        // 保存角色菜单关系
        this.sysRoleMenuService.saveOrUpdate(sysRole.getId(), sysRoleDto.getMenuIds());
    }

    @Override
    public void update(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        // 更新角色
        this.baseMapper.updateById(sysRole);

        // 更新角色菜单关系
        this.sysRoleMenuService.saveOrUpdate(sysRoleDto.getId(), sysRoleDto.getMenuIds());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull).toList();
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }

        // 删除角色
        this.baseMapper.deleteBatchIds(ids);

        // 删除用户角色关系
        this.sysUserRoleService.deleteByRoleIds(ids);

        // 删除角色菜单关系
        this.sysRoleMenuService.deleteByRoleIds(ids);

        // 删除角色数据权限关系
        this.sysRoleDataScopeService.deleteByRoleIds(ids);
    }

    @Override
    public PageResult<SysRoleVo> page(SysRolePageDto sysRolePageDto) {

        IPage<SysRole> page = this.baseMapper.selectPage(getPage(sysRolePageDto), getWrapper(sysRolePageDto));

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
        wrapper.eq(Objects.nonNull(sysRolePageDto.getTenantId()), SysRole::getTenantId, sysRolePageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getName()), SysRole::getName, sysRolePageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getCode()), SysRole::getCode, sysRolePageDto.getCode());

        if (sysRolePageDto.getCreatedTimeStart() != null && sysRolePageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysRole::getCreatedTime, sysRolePageDto.getCreatedTimeStart(), sysRolePageDto.getCreatedTimeEnd());
        } else if (sysRolePageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysRole::getCreatedTime, sysRolePageDto.getCreatedTimeStart());
        } else if (sysRolePageDto.getCreatedTimeEnd() != null) {
            wrapper.ge(SysRole::getCreatedTime, sysRolePageDto.getCreatedTimeEnd());
        }

        // 数据权限
        dataScopeWrapper(wrapper);

        return wrapper;
    }

    @Override
    public void export(SysRolePageDto sysRolePageDto) {
        List<SysRole> sysRoleList = this.baseMapper.selectList(getWrapper(sysRolePageDto));
        List<SysRoleVo> sysRoleVoList = SysRoleConvert.INSTANCE.convert(sysRoleList);
        ExcelUtils.export(SysRoleVo.class, "系统角色", "系统角色", sysRoleVoList);
    }

    @Override
    public List<SysRoleVo> list(SysRolePageDto sysRolePageDto) {
        List<SysRole> sysRoleList = this.baseMapper.selectList(getWrapper(sysRolePageDto));

        return SysRoleConvert.INSTANCE.convert(sysRoleList);
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        return this.baseMapper.selectRolePermissionByUserId(userId);
    }

    @Override
    public List<SysRole> selectRolesByUsername(String username) {
        return this.baseMapper.selectRolesByUsername(username);
    }

    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        return null;
    }

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
        if (dataScopeDto.getDataScope().equals(DataScopeType.CUSTOM.value())) {
            this.sysRoleDataScopeService.saveOrUpdate(dataScopeDto.getId(), dataScopeDto.getOrgIds());
        } else {
            this.sysRoleDataScopeService.deleteByRoleIds(Collections.singletonList(dataScopeDto.getId()));
        }

    }

}
