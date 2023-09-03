package com.brycehan.boot.common.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.brycehan.boot.common.base.entity.BaseEntity;

/**
 * 基础 Mapper
 *
 * @author Bryce Han
 * @since 2022/1/1
 */
public interface BryceBaseMapper<T extends BaseEntity> extends BaseMapper<T> {

}
