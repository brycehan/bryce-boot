package com.brycehan.boot.common.base.id;

import java.util.UUID;

/**
 * UUID的ID生成器
 *
 * @author Bryce Han
 * @since 2022/5/16
 */
public class IdGenerator {

    /**
     * 生成ID
     *
     * @return id
     */
    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
