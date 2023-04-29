package com.brycehan.boot.framework.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.brycehan.boot.system.context.LoginUser;
import com.brycehan.boot.system.context.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * Mybatis Plus字段填充处理器
 *
 * @author Bryce Han
 * @since 2022/5/12
 */
@Configuration
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = LoginUserContext.currentUser();
        setFieldValByName("createUserId", loginUser.getId(), metaObject);
        setFieldValByName("createUsername", loginUser.getUsername(), metaObject);
        setFieldValByName("createTime", LocalDateTime.now(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LoginUser loginUser = LoginUserContext.currentUser();
        setFieldValByName("updateUserId", loginUser.getId(), metaObject);
        setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

}
