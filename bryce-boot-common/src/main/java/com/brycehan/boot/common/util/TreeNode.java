package com.brycehan.boot.common.util;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树节点
 * 所有需要实现树结构的，需要继承该类
 *
 * @since 2023/8/1
 * @author Bryce Han
 */
@Data
public class TreeNode<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 上级ID
     */
    private Long parentId;

    /**
     * 子节点列表
     */
    private List<T> children = new ArrayList<>();

}
