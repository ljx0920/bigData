package com.tscloud.common.framework.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 继承自该类的entity表示是可被跟踪的，可以查询对应实体
 *
 * @author daowan.hu
 */
public class TrackableEntity implements Serializable {

    private static final long serialVersionUID = -4052705808523280313L;

    @ApiModelProperty(value = "记录主键", notes = "自动生成，自增")
    protected Integer id;

    @ApiModelProperty(value = "记录创建时间", example = "2016-08-01 12:24:36")
    protected Date createDate = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
