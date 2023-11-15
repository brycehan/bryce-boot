package com.brycehan.boot;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BryceServerApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        System.out.println("SecretKey012345678901234567890123456789012345678901234567890123456789".length());
        System.out.println(RandomStringUtils.randomAlphanumeric(69));
    }
}
