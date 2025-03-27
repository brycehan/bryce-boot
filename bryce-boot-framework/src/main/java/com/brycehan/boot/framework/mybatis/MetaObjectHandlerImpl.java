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
            // 创建者所属部门
            strictInsertFill(metaObject, "deptId", Long.class, loginUser.getDeptId());
            // 更新者ID
            strictInsertFill(metaObject, "updatedUserId", Long.class, loginUser.getId());
        }

        LocalDateTime now = LocalDateTime.now();
        // 创建时间
        strictInsertFill(metaObject, "createdTime", LocalDateTime.class, now);
        // 更新时间
        strictInsertFill(metaObject, "updatedTime", LocalDateTime.class, now);
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
