package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.system.entity.po.SysDept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统部门Mapper接口
 *
 * @since 2023/08/31
 * @author Bryce Han
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 部门列表
     *
     * @param params 参数
     * @return 部门列表
     */
    List<SysDept> list(Map<String, Object> params);

}
