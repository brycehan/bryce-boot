package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.dto.SysLoginInfoPageDto;
import com.brycehan.boot.system.entity.SysLoginInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统登录信息Mapper接口
 *
 * @author Bryce Han
 * @since 2022/9/20
 */
@Mapper
public interface SysLoginInfoMapper extends BryceBaseMapper<SysLoginInfo> {

    /**
     * 分页查询
     *
     * @param sysLoginInfoPageDto 系统登录信息分页数据传输对象
     * @return 系统登录信息列表
     */
    List<SysLoginInfo> page(SysLoginInfoPageDto sysLoginInfoPageDto);
}
