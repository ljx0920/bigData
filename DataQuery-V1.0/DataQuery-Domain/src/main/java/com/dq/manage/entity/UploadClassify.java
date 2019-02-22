package com.dq.manage.entity;

import com.iov.common.framework.domain.TrackableEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wei.wang5  ${date}
 */
@Getter
@Setter
@ApiModel(value = "UploadClassify", description = "数据管理-上传分类树")
public class UploadClassify extends TrackableEntity {


    @ApiModelProperty(value = "父级菜单id")
    private String parentId;
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @ApiModelProperty(value = "菜单级别")
    private String level;
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    @ApiModelProperty(value = "菜单显示名")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @ApiModelProperty(value = "菜单描述")
    private String description;
    public void setDescription(String description) {
        this.description = description;
    }

    @ApiModelProperty(value = "菜单顺序项")
    private Integer itemOrder;
    public void setItemOrder(Integer itemOrder) {
        this.itemOrder = itemOrder;
    }

    @ApiModelProperty(value = "菜单项图标")
    private String icon;
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @ApiModelProperty(value = "发布状态")
    private String status;
    public void setStatus(String status) {
        this.icon = status;
    }

    @ApiModelProperty(value = "菜单显示名")
    private String name1;
    public String getName1() {
        return name1;
    }
    public void setName1(String name1) {
        this.name1 = name1;
    }

    @ApiModelProperty(value = "菜单显示名")
    private String date;
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }



    //private UploadClassify(){};

    public static UploadClassify newInstance() {
        return new UploadClassify();
    }

}
