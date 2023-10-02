package com.brycehan.boot.common.util;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 树形结构工具类
 *
 * @author Bryce Han
 * @since 2023/8/1
 */
public class TreeUtils {

    /**
     * 根据parentId，构建树节点
     *
     * @param treeNodes 所有树节点
     * @param parentId 上级ID
     * @return 树结构数据
     * @param <T> 节点类型
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes, Long parentId){
        Assert.notNull(parentId, "parentId不能为空");

        List<T> list = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if(parentId.equals(treeNode.getParentId())){
                list.add(findChildren(treeNodes, treeNode));
            }
        }

        return list;
    }

    /**
     * 封装根节点数据，添加子节点
     *
     * @param treeNodes 树节点
     * @param rootNode 当前根节点
     * @return 封装后的节点
     * @param <T> 节点类型
     */
    private static <T extends TreeNode<T>> T findChildren(List<T> treeNodes, T rootNode){
        for (T treeNode: treeNodes) {
            if(rootNode.getId().equals(treeNode.getParentId())){
                rootNode.getChildren().add(findChildren(treeNodes, treeNode));
            }
        }
        return rootNode;
    }

    /**
     * 构建树节点
     *
     * @param treeNodes 所有节点数据
     * @return 树结构数据
     * @param <T> 节点类型
     */
    public static <T extends TreeNode<T>> List<T> build(List<T> treeNodes){
        List<T> list = new ArrayList<>();

        Map<Long, T> nodeMap = new LinkedHashMap<>(treeNodes.size());
        for (T treeNode : treeNodes) {
            nodeMap.put(treeNode.getId(), treeNode);
        }

        for (T node : nodeMap.values()) {
            T parent = nodeMap.get(node.getParentId());
            if(parent != null){
                parent.getChildren().add(node);
                continue;
            }

            list.add(node);
        }
        return list;
    }

}
