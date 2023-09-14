package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.common.util.ExcelUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.convert.SysRoleConvert;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysRoleService;
import com.brycehan.boot.system.vo.SysRoleVo;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    private Wrapper<SysRole> getWrapper(SysRolePageDto sysRolePageDto){
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Objects.nonNull(sysRolePageDto.getStatus()), SysRole::getStatus, sysRolePageDto.getStatus());
        wrapper.eq(Objects.nonNull(sysRolePageDto.getOrgId()), SysRole::getOrgId, sysRolePageDto.getOrgId());
        wrapper.eq(Objects.nonNull(sysRolePageDto.getTenantId()), SysRole::getTenantId, sysRolePageDto.getTenantId());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getName()), SysRole::getName, sysRolePageDto.getName());
        wrapper.like(StringUtils.isNotEmpty(sysRolePageDto.getCode()), SysRole::getCode, sysRolePageDto.getCode());

        if(sysRolePageDto.getCreatedTimeStart() != null && sysRolePageDto.getCreatedTimeEnd() != null) {
            wrapper.between(SysRole::getCreatedTime, sysRolePageDto.getCreatedTimeStart(), sysRolePageDto.getCreatedTimeEnd());
        } else if(sysRolePageDto.getCreatedTimeStart() != null) {
            wrapper.ge(SysRole::getCreatedTime, sysRolePageDto.getCreatedTimeStart());
        }else if(sysRolePageDto.getCreatedTimeEnd() != null) {
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
}
