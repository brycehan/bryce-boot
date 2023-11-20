package com.brycehan.boot.system.mapper;

import com.brycehan.boot.framework.mybatis.mapper.BryceBaseMapper;
import com.brycehan.boot.system.entity.SysTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统租户Mapper接口
 *
 * @since 2023/11/06
 * @author Bryce Han
 */
@Mapper
public interface SysTenantMapper extends BryceBaseMapper<SysTenant> {

}
