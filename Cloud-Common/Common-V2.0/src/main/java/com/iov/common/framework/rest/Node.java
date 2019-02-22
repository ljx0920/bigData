package com.iov.common.framework.rest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author bo.zhou1
 * @date 2018/1/18
 */
@Getter
@Setter
@ApiModel(value = "Node", description = "层级目录节点")
public class Node implements Serializable {
    private static final long serialVersionUID = -3116372888315114025L;

    @ApiModelProperty(value = "节点ID")
    private Integer id;

    @ApiModelProperty(value = "父节点ID")
    private Integer pId;

    @ApiModelProperty(value = "节点名称")
    private String name;

    @ApiModelProperty(value = "子节点")
    private List<Node> children;

    @ApiModelProperty(value = "节点扩展属性")
    private Object data;

    @ApiModelProperty(value = "是否默认选中")
    private boolean checked;

    @ApiModelProperty(value = "是否禁用节点选择框")
    private boolean chkDisabled;

    @ApiModelProperty(value = "扩展节点标记")
    private boolean flag;

    @ApiModelProperty(value = "节点是否可操作，根据页面扩展属性")
    private boolean disabled;

    @ApiModelProperty(value = "节点是否打开")
    private boolean open;

    /**
     * 判断是否是其他节点,如是其他的为1,是树本身为0
     * 目前用到这一判断的地方为主对象分类挂载主对象
     */
    @ApiModelProperty(value = "判断是否是其他节点")
    private Integer isOtherBean;

    public Node() {
    }

    public Node(Integer id, String name, Object data, Integer pId) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.pId = pId;
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
                if (nodeMap.get(node.getPId()) != null) {
                    if (nodeMap.get(node.getPId()).getChildren() == null) {
                        nodeMap.get(node.getPId()).setChildren(Lists.newArrayList());
                    }
                    nodeMap.get(node.getPId()).getChildren().add(node);
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
