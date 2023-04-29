package com.brycehan.boot.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.base.entity.PageResult;
import com.brycehan.boot.system.dto.DeleteDto;
import com.brycehan.boot.system.dto.SysPostDto;
import com.brycehan.boot.system.dto.SysPostPageDto;
import com.brycehan.boot.system.entity.SysPost;
import com.brycehan.boot.system.vo.SysPostVo;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * 系统岗位服务
 *
 * @author Bryce Han
 * @since 2022/10/31
 */
@Validated
public interface SysPostService extends IService<SysPost> {

    /**
     * 添加系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     */
    void save(SysPostDto sysPostDto);

    /**
     * 更新系统岗位
     *
     * @param sysPostDto 系统岗位Dto
     */
    void update(SysPostDto sysPostDto);

    /**
     * 删除系统岗位
     *
     * @param deleteDto 系统岗位删除Dto
     */
    void delete(DeleteDto deleteDto);

    /**
     * 系统岗位分页查询信息
     *
     * @param sysPostPageDto 系统岗位分页搜索条件
     * @return 分页信息
     */
    PageResult<SysPostVo> page(@NotNull SysPostPageDto sysPostPageDto);

    /**
     * 系统岗位导出数据
     *
     * @param sysPostPageDto 系统岗位分页搜索条件
     * @return 系统岗位列表
     */
    void export(@NotNull SysPostPageDto sysPostPageDto);

    /**
     * 根据用户账号查询所属岗位组
     *
     * @param username 用户账号
     * @return 所属岗位组
     */
    List<SysPost> selectPostsByUsername(String username);

}
