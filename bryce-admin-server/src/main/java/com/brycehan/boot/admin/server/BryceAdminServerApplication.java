package com.brycehan.boot.admin.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.brycehan.boot.*.mapper")
@SpringBootApplication(scanBasePackages = "com.brycehan.boot")
public class BryceAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceAdminServerApplication.class, args);
    }

}
