package com.brycehan.boot.common.base.id;

import com.github.yitter.idgen.YitIdHelper;

/**
 * 分布式ID生成器
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
    public static Long nextId() {
        return YitIdHelper.nextId();
    }

}
