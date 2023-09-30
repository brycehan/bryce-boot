package com.brycehan.boot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.brycehan.boot.common.base.entity.BaseEntity;
import java.io.Serial;

/**
 * 系统用户岗位关系entity
 *
 * @author Bryce Han
 * @since 2023/09/30
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("brc_sys_user_post")
public class SysUserPost extends BaseEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;

}
