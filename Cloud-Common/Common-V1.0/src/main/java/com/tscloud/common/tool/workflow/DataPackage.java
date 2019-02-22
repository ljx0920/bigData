package com.tscloud.common.tool.workflow;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * @date 2017/3/14
 */
public class DataPackage implements Serializable {

    //任务id
    private int taskId = 0;
    //批次NO
    private String batchNum;
    private String name;

    private String dataType;
    private String flag;

    public String getBatchNum() {
        return batchNum;
    }
    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    /**
     * 数据包信息的表头信息
     */
    private List<Header> headerList = Lists.newArrayList();

    /**
     * 数据体
     */
    private List<Object> dataList = Lists.newArrayList();

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public List<Header> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<Header> headerList) {
        this.headerList = headerList;
    }

    public List<Object> getDataList() {
        return dataList;
    }

    public void setDataList(List<Object> dataList) {
        this.dataList = dataList;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getValue(String fieldName) {
        int fieldIndex = -1;
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.get(i).getFieldName().equals(fieldName)) {
                fieldIndex = i;
                break;
            }
        }
        if (fieldIndex == -1) {
            return null;
        }
        return dataList.get(fieldIndex);
    }

    /**
     * 获取所有数据的第index个的字段fieldName下对应的值
     */
    public Object getArrayValue(int index, String fieldName) {
        Object values = dataList.get(index);
        if (values instanceof List) {
            List<Object> list = (List<Object>) values;
            int fieldIndex = -1;
            for (int i = 0; i < headerList.size(); i++) {
                if (headerList.get(i).getFieldName().equals(fieldName)) {
                    fieldIndex = i;
                    break;
                }
            }
            if (fieldIndex == -1) {
                return null;
            }
            return list.get(fieldIndex);
        }
        throw new RuntimeException("data content error.");
    }
}
