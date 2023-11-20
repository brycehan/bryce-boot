package com.brycehan.boot.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysParam;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统参数Mapper接口
 *
 * @since 2023/09/28
 * @author Bryce Han
 */
@Mapper
public interface SysParamMapper extends BryceBaseMapper<SysParam> {

    default boolean exists(String paramKey) {
        return this.exists(new LambdaQueryWrapper<SysParam>().eq(SysParam::getParamKey, paramKey));
    }

    default SysParam selectOne(String paramKey) {
        return this.selectOne(new LambdaQueryWrapper<SysParam>().eq(SysParam::getParamKey, paramKey));
    }

}
