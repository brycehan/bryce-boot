package com.brycehan.boot.common.base.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

/**
 * 基础分页VO视图对象
 *
 * @since 2021/8/31
 * @author Bryce Han
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class BasePageVo<T> extends BaseEntity {

    /**
     * 总条数
     */
    @Schema(description = "总条数")
    private Long total;

    /**
     * 当前页数
     */
    @Schema(description = "当前页数")
    private Integer pageNum;

    /**
     * 每页数量
     */
    @Schema(description = "每页数量")
    private Integer size;

    /**
     * 分页数据
     */
    @Schema(description = "分页数据")
    private List<T> list;
}
