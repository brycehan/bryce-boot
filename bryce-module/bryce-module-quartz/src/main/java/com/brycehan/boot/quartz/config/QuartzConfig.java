package com.brycehan.boot.quartz.config;

import com.brycehan.boot.common.constant.DataConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Quartz 定时任务配置
 *
 * @since 2023/10/18
 * @author Bryce Han
 */
@Configuration
public class QuartzConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        // quartz 参数
        Properties properties = new Properties();
        properties.put("org.quartz.scheduler.instanceName", "BryceScheduler");
        properties.put("org.quartz.scheduler.instanceId", "AUTO");

        // 线程池配置
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.put("org.quartz.threadPool.threadCount", "20");
        properties.put("org.quartz.threadPool.threadPriority", "5");

        // jobStore 配置
        properties.put("org.quartz.jobStore.class", "org.springframework.scheduling.quartz.LocalDataSourceJobStore");

        // 集群配置
        properties.put("org.quartz.jobStore.isClustered", "true");
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        properties.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        properties.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");

        properties.put("org.quartz.jobStore.misfireThreshold", "12000");
        properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        properties.put("org.quartz.jobStore.selectWithLockSQL", "select * from {0}LOCKS UPDLOCK where LOCK_NAME = ?");

        // PostgreSQL 数据库配置
        if(DataConstants.PG_DRIVER.equals(driverClassName)) {
            properties.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");
        }

        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setSchedulerName("BryceScheduler");
        factoryBean.setDataSource(dataSource);
        factoryBean.setQuartzProperties(properties);

        // 延时启动
//        factoryBean.setStartupDelay(10);
        factoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 启动时更新已存在的 Job，这样就不用每次修改 targetObject 后删除 qrtz_job_details 表对应的记录了
        factoryBean.setOverwriteExistingJobs(true);

        return factoryBean;
    }
}
