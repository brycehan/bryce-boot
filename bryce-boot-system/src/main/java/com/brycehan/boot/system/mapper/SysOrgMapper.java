package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.system.entity.po.SysOrg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统机构Mapper接口
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Mapper
public interface SysOrgMapper extends BaseMapper<SysOrg> {

    /**
     * 机构列表
     *
     * @param params 参数
     * @return 机构列表
     */
    List<SysOrg> list(Map<String, Object> params);

}
