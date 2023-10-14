package com.brycehan.boot.common.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.brycehan.boot.common.util.JsonUtils;
import com.fhs.core.trans.vo.TransPojo;
import lombok.Data;
import java.time.LocalDateTime;
import java.io.Serial;
import java.io.Serializable;

/**
 * Entity 基类
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
public abstract class BaseEntity implements TransPojo, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 版本号
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 删除标识（false：正常，true：删除）
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;

    /**
     * 创建者ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createdUserId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 修改者ID
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updatedUserId;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;

    public String toString(){
        return JsonUtils.writeValueAsString(this);
    }

}
