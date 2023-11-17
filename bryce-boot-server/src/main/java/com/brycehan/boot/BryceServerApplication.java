package com.brycehan.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.brycehan.boot.*.mapper")
@SpringBootApplication
public class BryceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BryceServerApplication.class, args);
    }

}
