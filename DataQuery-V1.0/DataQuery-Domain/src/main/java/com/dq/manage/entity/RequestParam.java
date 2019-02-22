package com.dq.manage.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wei.wang5  ${date}
 */
@Getter
@Setter
@ApiModel(value = "node", description = "节点")
public class RequestParam implements Serializable{

    @ApiModelProperty(value = "名称")
    private String name;
    @ApiModelProperty(value = "值")
    private String value;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "操作")
    private String operation;

    private RequestParam() {}

    public  static  RequestParam newInstance() {
        return new RequestParam();
    }

}
