package com.brycehan.boot.system.api;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.brycehan.boot.api.system.BpmPostApi;
import com.brycehan.boot.api.system.vo.BpmPostVo;
import com.brycehan.boot.system.entity.convert.SysPostConvert;
import com.brycehan.boot.system.entity.po.SysPost;
import com.brycehan.boot.system.service.SysPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 系统参数 Api 实现
 *
 * @author Bryce Han
 * @since 2023/11/16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BpmPostApiService implements BpmPostApi {

    private final SysPostService sysPostService;

    @Override
    public void validatePostList(Collection<Long> postIds) {
        sysPostService.validatePostList(postIds);
    }

    @Override
    public List<BpmPostVo> getPostList(Collection<Long> ids) {
        LambdaQueryWrapper<SysPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysPost::getId, ids);
        List<SysPost> list = sysPostService.list(queryWrapper);
        return SysPostConvert.INSTANCE.convertBpm(list);
    }
}
