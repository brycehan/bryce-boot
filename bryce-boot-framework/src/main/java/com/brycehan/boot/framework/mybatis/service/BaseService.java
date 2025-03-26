package com.brycehan.boot.framework.mybatis.service;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.entity.dto.IdsDto;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * 基础服务
 *
 * @since 2022/9/16
 * @author Bryce Han
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 批量删除
     *
     * @param idsDto ids参数
     */
    @Transactional
    default void delete(IdsDto idsDto) {
        getBaseMapper().deleteByIds(idsDto.getIds());
    }

    /**
     * 判断是否存在
     *
     * @param id 主键
     * @return 是否存在
     */
    default boolean exists(Serializable id) {
        if (ObjectUtil.isEmpty(id)) {
            return false;
        }
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return getBaseMapper().exists(queryWrapper);
    }

}
