package com.brycehan.boot.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan(basePackages = "com.brycehan.boot.*.mapper")
@ComponentScan(basePackages = "com.brycehan.boot")
@SpringBootApplication
public class BryceAdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceAdminServerApplication.class, args);
    }

}
