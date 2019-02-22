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
@ApiModel(value = "DataManagerConfigure", description = "数据管理配置中心")
public class DataManagerConfigure extends TrackableEntity {

    @ApiModelProperty(value = "数据种类")
    private String dataType;

    @ApiModelProperty(value = "服务器数据存储路径")
    private String serverStoreDir;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "大数据平台查询地址")
    private String queryTarget;

    @ApiModelProperty(value = "大数据平台采集地址")
    private String executeTarget;

    private DataManagerConfigure() {};

    public DataManagerConfigure newInstance() { return new DataManagerConfigure(); }

}
