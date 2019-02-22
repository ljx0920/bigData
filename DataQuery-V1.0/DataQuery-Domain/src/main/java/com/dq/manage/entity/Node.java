package com.dq.manage.entity;

import com.iov.common.framework.domain.TrackableEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @author wei.wang5  ${date}
 */
@Getter
@Setter
@ApiModel(value = "node", description = "节点")
public class Node  implements Serializable {
    @ApiModelProperty(value = "树节点id")
    private String id;
    @ApiModelProperty(value = "父节点id")
    private String parentId;
    @ApiModelProperty(value = "树节点名称")
    private String name;
    @ApiModelProperty(value = "树节点描述")
    private String description;
    @ApiModelProperty(value = "节点图标")
    private String icon;
    @ApiModelProperty(value = "级别")
    private String level;
    @ApiModelProperty(value = "树节点下级节点列表")
    private List<Node> children;
    @ApiModelProperty(value = "默认是否展开")
    private boolean isExpand;
    @ApiModelProperty(value = "是否为叶子节点")
    private boolean isLeaf;
    @ApiModelProperty(value = "节点表示的实体对象信息")
    private Object data;


    private Node(){}

    public static Node newInstance() {return new Node(); }
}
