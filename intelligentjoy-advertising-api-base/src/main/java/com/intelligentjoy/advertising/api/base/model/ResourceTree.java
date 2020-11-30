package com.intelligentjoy.advertising.api.base.model;

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
 */
public class ResourceTree implements Serializable {

    private static final long serialVersionUID = 3544897406484560446L;

    private List<Resource> resourceList = null;

    public ResourceTree() {
        resourceList = Collections.emptyList();
    }

    public ResourceTree(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    /**
     * 建立树形结构
     *
     * @return
     */
    public List<Resource> buildTree() {
        if (resourceList == null || resourceList.isEmpty()) {
            return Collections.emptyList();
        }
        List<Resource> treeResources = resourceList.stream().filter(e -> 0 == e.getParentId())
                .map(node -> buildChildTree(node))
                .sorted(Comparator.comparingInt(Resource::getOrder))
                .collect(Collectors.toList());
        return treeResources;
    }

    /**
     * 递归，建立子树形结构
     *
     * @param pNode
     * @return
     */
    private Resource buildChildTree(Resource pNode) {
        List<Resource> childResources = resourceList.stream().filter(e -> pNode.getResourceId().equals(e.getParentId()))
                .map(node -> buildChildTree(node))
                .sorted(Comparator.comparingInt(Resource::getOrder))
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
        resources = new ResourceTree(resources).buildTree();
    }
}