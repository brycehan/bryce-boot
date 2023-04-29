package com.brycehan.boot.framework.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.brycehan.boot.common.base.id.IdGenerator;
import org.springframework.stereotype.Component;

/**
 * ID配置
 *
 * @author Bryce Han
 * @since 2022/5/13
 */
@Component
public class IdConfig implements IdentifierGenerator {

    @Override
    public Number nextId(Object entity) {
        return null;
    }

    @Override
    public String nextUUID(Object entity) {
        System.out.println("next uuid 生成成功！");
        return IdGenerator.generate();
    }

}
