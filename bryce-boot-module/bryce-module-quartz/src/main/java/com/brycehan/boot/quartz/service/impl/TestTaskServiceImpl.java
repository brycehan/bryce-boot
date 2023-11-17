package com.brycehan.boot.quartz.service.impl;

import com.brycehan.boot.quartz.service.TestTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 测试任务服务实现
 *
 * @since 2023/10/20
 * @author Bryce Han
 */
@Slf4j
@Service
public class TestTaskServiceImpl implements TestTaskService {

    @Override
    public void run(String params) {
        log.info("任务 testTaskService.run({})，开始执行...", params);
        log.info("任务 testTaskService.run({})，执行结束", params);
    }
}
