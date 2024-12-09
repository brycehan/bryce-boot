package com.brycehan.boot.framework.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.brycehan.boot.framework.mybatis.interceptor.DataScopeInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 * @since 2022/11/10
 * @author Bryce Han
 */
@Configuration
@ConditionalOnClass(value = {MybatisPlusAutoConfiguration.class})
public class MybatisPlusConfig {

    /**
     * 配置MybatisPlus拦截器
     *
     * @return MybatisPlus拦截器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 数据权限
        interceptor.addInnerInterceptor(new DataScopeInnerInterceptor());
        // 防止全表更新与删除
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(1000L); // 单页限制
        interceptor.addInnerInterceptor(paginationInnerInterceptor);

        return interceptor;
    }

    @Bean
    public MetaObjectHandler metaObjectHandler(){
        return new MetaObjectHandlerImpl();
    }
}
