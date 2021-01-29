package com.intelligentjoy.advertising.api.base.model.tree;

import java.util.List;

/**
 * 树形结构
 * @author BruceTim
 * @date 2020-12-04
 */
public interface TreeNode {

    /**
     * ID
     *
     * @return
     */
    Number getId();

    /**
     * 父级ID
     *
     * @return
     */
    Number getParentId();

    /**
     * 展示排序
     *
     * @return
     */
    Integer getOrder();

    /**
     * 获取子节点
     *
     * @return
     */
    List<? extends TreeNode> getChildren();

    /**
     * 设置子节点
     *
     * @param children
     */
    void setChildren(List<? extends TreeNode> children);
}
