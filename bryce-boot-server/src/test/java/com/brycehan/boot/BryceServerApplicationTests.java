package com.brycehan.boot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BryceServerApplicationTests {

    @Test
    void contextLoads() {
        log.info("contextLoads");
    }

}
