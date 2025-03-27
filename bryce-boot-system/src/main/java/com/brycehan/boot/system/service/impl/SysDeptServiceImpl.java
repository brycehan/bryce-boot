package com.brycehan.boot.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.brycehan.boot.common.base.IdGenerator;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import com.brycehan.boot.common.base.ServerException;
import com.brycehan.boot.common.base.response.SystemResponseStatus;
import com.brycehan.boot.common.constant.DataConstants;
import com.brycehan.boot.common.entity.dto.IdsDto;
import com.brycehan.boot.common.util.TreeUtils;
import com.brycehan.boot.framework.mybatis.service.impl.BaseServiceImpl;
import com.brycehan.boot.system.entity.convert.SysDeptConvert;
import com.brycehan.boot.system.entity.dto.SysDeptDto;
import com.brycehan.boot.system.entity.po.SysDept;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.entity.vo.SysDeptVo;
import com.brycehan.boot.system.mapper.SysDeptMapper;
import com.brycehan.boot.system.service.SysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 系统部门服务实现类
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends BaseServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    /**
     * 添加系统部门
     *
     * @param sysDeptDto 系统部门Dto
     */
    public void save(SysDeptDto sysDeptDto) {
        SysDept sysDept = SysDeptConvert.INSTANCE.convert(sysDeptDto);
        sysDept.setId(IdGenerator.nextId());
        baseMapper.insert(sysDept);
    }

    @Override
    public void update(SysDeptDto sysDeptDto) {
        checkDeptDataScope(sysDeptDto.getId());
        SysDept sysDept = SysDeptConvert.INSTANCE.convert(sysDeptDto);

        // 上级部门不能为自身
        if (sysDept.getId().equals(sysDeptDto.getParentId())) {
            throw new RuntimeException("上级部门不能为自身");
        }

        // 上级部门不能为下级
        List<Long> subDeptIds = getSubDeptIds(sysDept.getId());
        if (subDeptIds.contains(sysDept.getParentId())) {
            throw new RuntimeException("上级部门不能为下级");
        }

        baseMapper.updateById(sysDept);
    }

    @Override
    @Transactional
    public void delete(IdsDto idsDto) {
        idsDto.getIds().forEach(this::checkDeptDataScope);
        // 判断是否有子部门
        long orgCount = count(new LambdaQueryWrapper<SysDept>().in(SysDept::getParentId, idsDto.getIds()));
        if (orgCount > 0) {
            throw new ServerException(SystemResponseStatus.ORG_EXIST_CHILDREN_CANNOT_BE_DELETED);
        }

        // 判断部门下面是否有用户
        Long userCount = Db.lambdaQuery(SysUser.class).in(SysUser::getDeptId, idsDto.getIds()).count();
        if (userCount > 0) {
            throw new ServerException(SystemResponseStatus.ORG_EXIST_CHILDREN_CANNOT_BE_DELETED);
        }

        // 删除
        baseMapper.deleteByIds(idsDto.getIds());
    }

    @Override
    public List<SysDeptVo> list(SysDeptDto sysDeptDto) {
        Map<String, Object> params = BeanUtil.beanToMap(sysDeptDto, false, false);
        // 数据权限过滤
        params.put(DataConstants.DATA_SCOPE, getDataScope("sd", "id"));

        // 部门列表
        List<SysDept> sysDeptList = baseMapper.list(params);
        return TreeUtils.build(SysDeptConvert.INSTANCE.convert(sysDeptList));
    }

    @Override
    public List<Long> getSubDeptIds(Long id) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(SysDept::getId, SysDept::getParentId);

        // 所有部门的id、parentId列表
        List<SysDept> orgList = baseMapper.selectList(queryWrapper);

        // 递归查询所有子部门IDs
        List<Long> subIds = new ArrayList<>();
        getTree(id, orgList, subIds);

        // 本部门也添加进去
        subIds.add(id);

        return subIds;
    }

    /**
     * 递归查询所有子部门ID列表
     *
     * @param id      当前部门ID
     * @param orgList 所有部门的列表
     * @param subIds  当前部门的子部门列表
     */
    private void getTree(Long id, List<SysDept> orgList, List<Long> subIds) {
        for (SysDept sysDept : orgList) {
            if (sysDept.getParentId().equals(id)) {
                getTree(sysDept.getId(), orgList, subIds);
                subIds.add(sysDept.getId());
            }
        }
    }

    @Override
    public String getDeptNameById(Long deptId) {
        if (deptId != null) {
            LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(SysDept::getName);
            queryWrapper.eq(SysDept::getId, deptId);
            SysDept sysDept = baseMapper.selectOne(queryWrapper, false);
            if (sysDept != null) {
                return sysDept.getName();
            }
        }
        return "";
    }

    @Override
    public void checkDeptDataScope(Long deptId) {
        if (deptId == null || LoginUser.isSuperAdmin(LoginUserContext.currentUser())) {
            return;
        }

        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getId, deptId);

        dataScopeWrapper(queryWrapper);
        if (!baseMapper.exists(queryWrapper)) {
            throw new RuntimeException("没有权限访问用户数据");
        }
    }
}
