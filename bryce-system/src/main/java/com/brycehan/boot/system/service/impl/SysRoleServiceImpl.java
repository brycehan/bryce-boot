package com.brycehan.boot.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.brycehan.boot.system.dto.SysRolePageDto;
import com.brycehan.boot.system.entity.SysRole;
import com.brycehan.boot.system.mapper.SysRoleMapper;
import com.brycehan.boot.system.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.github.pagehelper.page.PageMethod.startPage;

/**
 * 系统角色服务实现类
 *
 * @author Bryce Han
 * @since 2022/5/15
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
    }

    @Override
    public PageInfo<SysRole> page(SysRolePageDto sysRolePageDto) {

        //1.根据page、size初始化分页参数，此时分页插件内部会将这两个参数放到ThreadLocal线程上下文中
        Page<SysRole> page = startPage(sysRolePageDto.getCurrent(), sysRolePageDto.getPageSize());

        /*
          2.紧跟初始化代码，查询业务数据
          2.1.分页拦截器根据page、size参数改写sql语句，生成count并查询
          2.2.分页拦截器执行实际的业务查询
          2.3.分页拦截器将2.1.的总记录数与2.2查询的分页数据封装到1中的page
          2.4.分页拦截器清空ThreadLocal上下文中记录的分页参数信息，防止内存泄漏
         */
        this.sysRoleMapper.page(sysRolePageDto);

        //3.转换为PageInfo后返回
        return new PageInfo<>(page);
    }

    @Override
    public Set<String> selectRolePermissionByUserId(String userId) {
        return this.sysRoleMapper.selectRolePermissionByUserId(userId);
    }

    @Override
    public List<SysRole> selectRolesByUsername(String username) {
        return this.sysRoleMapper.selectRolesByUsername(username);
    }

    @Override
    public List<SysRole> selectRolesByUserId(String userId) {
        return null;
    }
}
