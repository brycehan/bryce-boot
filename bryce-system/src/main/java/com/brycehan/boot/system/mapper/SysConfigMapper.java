package com.brycehan.boot.system.mapper;

import com.brycehan.boot.common.base.mapper.BryceBaseMapper;
import com.brycehan.boot.system.dto.SysConfigPageDto;
import com.brycehan.boot.system.entity.SysConfig;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统配置Mapper接口
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
@Mapper
public interface SysConfigMapper extends BryceBaseMapper<SysConfig> {

    /**
     * 分页查询
     *
     * @param sysConfigPageDto 系统配置分页数据传输对象
     * @return 系统配置列表
     */
    List<SysConfig> page(SysConfigPageDto sysConfigPageDto);
}
