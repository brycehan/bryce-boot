package com.brycehan.boot.framework.config;

import com.brycehan.boot.common.util.FileUploadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;

/**
 * 应用变量的初始化
 *
 * @author Bryce Han
 * @since 2022/11/4
 */
@Slf4j
@Configuration
public class ApplicationVariableInitializer implements EnvironmentAware {

    private Environment environment;

    /**
     * 初始化应用变量
     */
    public void initVariables() {
        log.info("初始化变量...");

        // 1、初始化变量
        FileUploadUtils.DEFAULT_BASE_DIR = System.getProperty("user.home")
                .concat(Objects.requireNonNull(this.environment.getProperty("server.servlet.context-path")))
                .concat(Objects.requireNonNull(this.environment.getProperty("bryce.application.upload-path")));

        log.info("初始化变量完成");
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        this.initVariables();
    }

}
