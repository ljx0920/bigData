package com.tscloud.common.framework.rest.view;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @author bo.zhou1
 * @date 2018/1/18
 */
public class Node implements java.io.Serializable {
    private static final long serialVersionUID = -3116372888315114025L;

    /**
     * 节点id
     */
    private Integer id;
    /**
     * 父节点id
     */
    private Integer pId;
    /**
     * 节点名称
     */
    private String name;
    /**
     * 子节点
     */
    private List<Node> children;
    /**
     * 节点扩展属性
     */
    private Object data;
    /**
     * 是否默认选中
     */
    private boolean checked;
    /**
     * 是否禁用节点选择框
     */
    private boolean chkDisabled;
    /**
     * 扩展节点标记
     */
    private boolean flag;
    /**
     * 节点是否可操作，根据页面扩展属性
     */
    private boolean disabled;
    /**
     * 节点是否打开
     */
    private boolean open;

    /**
     * 判断是否是其他节点,如是其他的为1,是树本身为0
     * 目前用到这一判断的地方为数据项分类挂载数据项
     */
    private Integer isOtherBean;

    public Node() {
    }

    public Node(Integer id, String name, Object data, Integer pId) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.pId = pId;
    }

    public Node(Integer id, String name, Object data, Integer pId, boolean open) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.pId = pId;
        this.open = open;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isChkDisabled() {
        return chkDisabled;
    }

    public void setChkDisabled(boolean chkDisabled) {
        this.chkDisabled = chkDisabled;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public Integer getIsOtherBean() {
        return isOtherBean;
    }

    public void setIsOtherBean(Integer isOtherBean) {
        this.isOtherBean = isOtherBean;
    }

    /**
     * 将Node集合[{id:1,pid:0},{id:2,pid:0},{id:0,pid:-1}]
     * 转换为[{id:0,pid:-1,children:[{id:1,pid:0},{id:2,pid:0}]}]
     *
     * @param nodeList node 集合
     * @return
     */
    public static List<Node> transformNodes(List<Node> nodeList) {
        List<Node> result = Lists.newArrayList();
        if (nodeList != null && !nodeList.isEmpty()) {
            Map<Integer, Node> nodeMap = Maps.newHashMap();
            nodeList.forEach(node -> nodeMap.put(node.getId(), node));
            nodeList.forEach(node -> {
                if (nodeMap.get(node.getpId()) != null) {
                    if (nodeMap.get(node.getpId()).getChildren() == null) {
                        nodeMap.get(node.getpId()).setChildren(Lists.newArrayList());
                    }
                    nodeMap.get(node.getpId()).getChildren().add(node);
                } else {
                    result.add(node);
                }
            });
        }
        return result;
    }

    /**
     * 将已转化好的树形结构的节点集nodeList，展开为平铺结构
     *
     * @param nodeList
     * @return List<Object>
     */
    public static List flatTree(List<Node> nodeList) {
        if (nodeList == null || nodeList.isEmpty()) {
            return Lists.newArrayList();
        }
        List<Object> result = Lists.newArrayList();
        for (Node node : nodeList) {
            result.addAll(flatTreeNode(node));
        }
        return result;
    }

    /**
     * 将单个节点，展开为平铺结构
     *
     * @param node
     * @return
     */
    private static List flatTreeNode(Node node) {
        List<Object> result = Lists.newArrayList();
        result.add(node.getData());
        result.addAll(flatTree(node.getChildren()));
        return result;
    }
}
