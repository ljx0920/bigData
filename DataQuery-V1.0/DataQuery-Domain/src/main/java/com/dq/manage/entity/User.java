package com.dq.manage.entity;

import com.iov.common.framework.domain.TrackableEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author wei.wang5
 */
@Getter
@Setter
@ApiModel(value = "User", description = "用户")
public class User extends TrackableEntity {

    public final static String USER = "user";

    @ApiModelProperty(value = "用户的角色id", example = "1")
    private Short roleId;

    @ApiModelProperty(value = "用户的角色id", example = "1")
    private Integer departmentId;

    @ApiModelProperty(value = "用户名", example = "租户")
    private String user;

    @ApiModelProperty(value = "密码", example = "xxxxxx")
    private String pwd;

    @ApiModelProperty(value = "生日", example = "1999-10-10")
    private Date birthday;

    @ApiModelProperty(value = "性别", example = "男")
    private String gender;

    @ApiModelProperty(value = "成员编码", example = "01")
    private String memberCode;

    @ApiModelProperty(value = "单位", example = "hirain")
    private String unit;

    @ApiModelProperty(value = "开始时间", example = "2018-03-01")
    private Date startDate;

    @ApiModelProperty(value = "结束时间", example = "2018-03-01")
    private Date endDate;

    @ApiModelProperty(value = "删除标志", example = "0 or 1")
    private Integer delFlag;

    @ApiModelProperty(value = "用户名字", example = "user")
    private String name;

    @ApiModelProperty(value = "用户手机号", example = "15222554869")
    private String mobilePhone;

    @ApiModelProperty(value = "父节点ID", example = "1")
    private Integer pId;

    private User() {}

    public User newInstance() { return new User(); }
}