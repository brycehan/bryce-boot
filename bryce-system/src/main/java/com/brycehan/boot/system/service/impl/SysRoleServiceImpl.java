package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.base.id.IdGenerator;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysRoleConvert;
import com.brycehan.boot.system.dto.SysRoleDto;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.vo.SysRoleVo;
import com.brycehan.boot.system.entity.SysRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 系统角色表服务实现类
 *
 * @author Bryce Han
 * @since 2023/08/24
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public void save(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        sysRole.setId(IdGenerator.nextId());
        this.baseMapper.insert(sysRole);
    }

    @Override
    public void update(SysRoleDto sysRoleDto) {
        SysRole sysRole = SysRoleConvert.INSTANCE.convert(sysRoleDto);
        this.baseMapper.updateById(sysRole);
    }

    @Override
    public PageResult<SysRoleVo> page(SysRolePageDto sysRolePageDto) {

        IPage<SysRole> page = this.baseMapper.selectPage(getPage(sysRolePageDto), getWrapper(sysRolePageDto));

        return new PageResult<>(page.getTotal(), SysRoleConvert.INSTANCE.convert(page.getRecords()));
    }

    /**
     * 封装查询条件
     *
     * @param sysRolePageDto 系统角色表分页dto
     * @return 查询条件Wrapper
     */
    private Wrapper<SysRole> getWrapper(SysRolePageDto sysRolePageDto){
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        return wrapper;
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
}
