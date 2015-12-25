package com.baidu.dingding.fragment.ShopingCar;

import java.util.List;

/**
 * Created by Administrator on 2015/12/15.根节点
 */
public class Node {

    String id;
    String name;
    List<ChildNode> childNodeList;

    public Node() {
    }

    public Node(String name, List<ChildNode> childNodeList, String id) {
        this.name = name;
        this.childNodeList = childNodeList;
        this.id = id;
    }

    public List<ChildNode> getChildNodeList() {
        return childNodeList;
    }

    public void setChildNodeList(List<ChildNode> childNodeList) {
        this.childNodeList = childNodeList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
