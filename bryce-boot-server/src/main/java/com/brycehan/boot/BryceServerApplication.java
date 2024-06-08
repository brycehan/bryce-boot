package com.brycehan.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Bryce 服务应用
 *
 * @since 2023/11/19
 * @author Bryce Han
 */
@EnableRetry
@EnableTransactionManagement
@SpringBootApplication
public class BryceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceServerApplication.class, args);
    }

}
