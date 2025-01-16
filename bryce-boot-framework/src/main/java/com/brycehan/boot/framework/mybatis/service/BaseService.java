package com.brycehan.boot.framework.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.entity.dto.IdsDto;
import org.springframework.transaction.annotation.Transactional;

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

}
