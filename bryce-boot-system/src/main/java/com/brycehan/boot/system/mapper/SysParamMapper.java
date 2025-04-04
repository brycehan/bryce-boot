package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.framework.mybatis.BryceBaseMapper;
import com.brycehan.boot.system.entity.po.SysParam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统参数Mapper接口
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@SuppressWarnings("all")
@Mapper
public interface SysParamMapper extends BryceBaseMapper<SysParam> {

    default boolean exists(String paramKey) {
        return exists(new LambdaQueryWrapper<SysParam>().eq(SysParam::getParamKey, paramKey));
    }

    default SysParam selectOne(String paramKey) {
        return selectOne(new LambdaQueryWrapper<SysParam>().eq(SysParam::getParamKey, paramKey), false);
    }

}
