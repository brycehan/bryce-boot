package com.brycehan.boot.framework.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.brycehan.boot.common.base.dto.IdsDto;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 基础服务
 *
 * @author Bryce Han
 * @since 2022/9/16
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 批量删除
     *
     * @param idsDto ids参数
     */
    @Transactional(rollbackFor = Exception.class)
    default void delete(IdsDto idsDto) {
        // 过滤无效参数
        List<Long> ids = idsDto.getIds().stream()
                .filter(Objects::nonNull)
                .toList();

        if (CollectionUtils.isNotEmpty(ids)) {
            this.getBaseMapper().deleteBatchIds(ids);
        }
    }

}
