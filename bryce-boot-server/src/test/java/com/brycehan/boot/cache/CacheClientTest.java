package com.brycehan.boot.cache;

import com.brycehan.boot.framework.common.utils.CacheClient;
import com.brycehan.boot.system.entity.po.SysUser;
import com.brycehan.boot.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

/**
 * 缓存客户端测试
 *
 * @author Bryce Han
 * @since 2024/8/21
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CacheClientTest {

    @Autowired
    private CacheClient cacheClient;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 测试设置缓存
     */
    @Test
    public void testSet() {
        SysUser sysUser = new SysUser();
        sysUser.setId(1L);
        sysUser.setUsername("test");
        sysUser.setPassword("test");

        String cacheKey = "test:" + SysUser.class.getSimpleName() + ":" + sysUser.getId();
        cacheClient.set(cacheKey, sysUser, 10, TimeUnit.MINUTES);
    }

    /**
     * 测试设置逻辑过期缓存
     */
    @Test
    public void testWithLogicalExpire() {
        SysUser sysUser = new SysUser();
        sysUser.setId(2L);
        sysUser.setUsername("test");
        sysUser.setPassword("test");

        String cacheKey = "test:" + SysUser.class.getSimpleName() + ":logicalExpire:" + sysUser.getId();
        cacheClient.setWithLogicalExpire(cacheKey, sysUser, 10, TimeUnit.MINUTES);
    }

    /**
     * 查询并缓存，通过互斥锁解决缓存击穿问题
     */
    @Test
    public void testQueryWithMutex() {
        SysUser sysUser = cacheClient.queryWithMutex("test:sysUser:mutex:", 1L, SysUser.class, sysUserService::getById, 10, TimeUnit.MINUTES);
        log.info("查询并缓存，通过互斥锁解决缓存击穿问题：{}", sysUser);
    }

    /**
     * 查询并缓存，通过逻辑过期解决缓存击穿问题
     */
    @Test
    public void testQueryWithLogicalExpire() {
        String keyPrefix = "test:" + SysUser.class.getSimpleName() + ":logicalExpire:";
        SysUser sysUser = cacheClient.queryWithLogicalExpire(keyPrefix, 2L, SysUser.class, sysUserService::getById, 10, TimeUnit.MINUTES);
        log.info("查询并缓存，通过逻辑过期解决缓存击穿问题：{}", sysUser);
    }

}
