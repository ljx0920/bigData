package com.dq.manage.entity;

import com.iov.common.framework.domain.TrackableEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author wei.wang5  ${date}
 */
@Getter
@Setter
@ApiModel(value = "UploadFileItem", description = "上传文件数据项")
public class UploadFileItem extends TrackableEntity {


    @ApiModelProperty(value = "数据项id")
    private Integer dataTypeId;


    @ApiModelProperty(value = "左侧树Id")
    private Integer uploadClassifyId;

    @ApiModelProperty(value = "数据项名称")
    private String dataTypeName;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件实际存储地址")
    private String filePath;

    @ApiModelProperty(value = "文件大小")
    private String fileSize;

    @ApiModelProperty(value = "导入文件人")
    private String importer;

    @ApiModelProperty(value = "导入时间")
    private String importTime;

    @ApiModelProperty(value = "数据处理批次号")
    private String batchNumber;

    @ApiModelProperty(value = "解析进度")
    private String analyticProgress;

    private UploadFileItem() {}

    public static UploadFileItem newInstance() {
        return new UploadFileItem();
    }
}
