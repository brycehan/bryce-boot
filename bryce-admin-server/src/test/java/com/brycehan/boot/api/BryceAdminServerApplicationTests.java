package com.brycehan.boot.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BryceAdminServerApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        Class cl = Double.class;
        String name = cl.getName();
        System.out.println(name);

    }
}
