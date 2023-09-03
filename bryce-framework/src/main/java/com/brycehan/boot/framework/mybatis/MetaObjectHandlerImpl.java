package com.brycehan.boot.framework.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.brycehan.boot.framework.security.context.LoginUser;
import com.brycehan.boot.framework.security.context.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * Mybatis Plus字段填充处理器
 *
 * @author Bryce Han
 * @since 2022/5/12
 */
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = LoginUserContext.currentUser();
        if(loginUser != null) {
            // 创建人ID
            strictInsertFill(metaObject, "createdUserId", Long.class, loginUser.getId());
            // 创建人所属机构
            strictInsertFill(metaObject, "orgId", Long.class, loginUser.getOrgId());
        }
        // 创建时间
        strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
        // 版本号
        strictInsertFill(metaObject, "version", Integer.class, 1);
        // 删除标识
        strictInsertFill(metaObject, "deleted", Boolean.class, false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新人ID
        strictUpdateFill(metaObject, "updatedUserId", Long.class, LoginUserContext.currentUserId());
        // 更新时间
        strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }

}
