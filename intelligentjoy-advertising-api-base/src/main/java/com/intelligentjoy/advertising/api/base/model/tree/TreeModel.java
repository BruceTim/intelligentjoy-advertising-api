package com.intelligentjoy.advertising.api.base.model.tree;

import com.intelligentjoy.advertising.api.base.model.Resource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限树
 *
 * @author BruceTim
 * @date 2020-12-04
 */
public class TreeModel<T extends TreeNode> implements Serializable {

    private static final long serialVersionUID = 3544897406484560446L;

    private List<T> nodes = null;

    public TreeModel() {
        nodes = Collections.emptyList();
    }

    public TreeModel(List<T> nodes) {
        this.nodes = nodes;
    }

    /**
     * 建立树形结构
     *
     * @return
     */
    public List<TreeNode> buildTree() {
        if (nodes == null || nodes.isEmpty()) {
            return Collections.emptyList();
        }
        List<TreeNode> nodeList = nodes.stream().filter(e -> 0 == e.getParentId())
                .map(node -> buildChildTree(node))
                .sorted(Comparator.comparingInt(TreeNode::getOrder))
                .collect(Collectors.toList());
        return nodeList;
    }

    /**
     * 递归，建立子树形结构
     *
     * @param pNode
     * @return
     */
    private TreeNode buildChildTree(TreeNode pNode) {
        List<TreeNode> childResources = nodes.stream().filter(e -> pNode.getId().equals(e.getParentId()))
                .map(node -> buildChildTree(node))
                .sorted(Comparator.comparingInt(TreeNode::getOrder))
                .collect(Collectors.toList());
        pNode.setChildren(childResources);
        return pNode;
    }

    public static void main(String[] args) {

        List<Resource> resources = new ArrayList<>(20);
        for (Integer i = 1; i <= 20; i++) {
            Resource resource = new Resource();
            resource.setResourceId(i);
            resource.setResourceCode(i.toString());
            resource.setActionUrl(i.toString());
            resource.setResourceName(i.toString());
            resource.setDescription(i.toString());
            resource.setParentId(i > 10 ? (i + 2) % 7 : (i - 1) % 5);
            resource.setType(1);
            resource.setLevel(1);
            resource.setOrder(i % 5);
            resources.add(resource);
        }
        resources.forEach(e -> System.out.println(e.getParentId()));
        resources = new TreeModel(resources).buildTree();
    }
}