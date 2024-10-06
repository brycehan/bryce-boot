package com.brycehan.boot.framework.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.brycehan.boot.common.base.LoginUser;
import com.brycehan.boot.common.base.LoginUserContext;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * Mybatis Plus字段填充处理器
 *
 * @since 2022/5/12
 * @author Bryce Han
 */
public class MetaObjectHandlerImpl implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LoginUser loginUser = LoginUserContext.currentUser();
        if (loginUser != null) {
            // 创建者ID
            strictInsertFill(metaObject, "createdUserId", Long.class, loginUser.getId());
            // 创建者所属机构
            strictInsertFill(metaObject, "orgId", Long.class, loginUser.getOrgId());
        }

        // 创建时间
        strictInsertFill(metaObject, "createdTime", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新者ID
        Long userId = LoginUserContext.currentUserId();
        if (userId != null) {
            strictUpdateFill(metaObject, "updatedUserId", Long.class, userId);
        }

        // 更新时间
        strictUpdateFill(metaObject, "updatedTime", LocalDateTime.class, LocalDateTime.now());
    }

}
