package com.tscloud.common.tool.workflow;

import java.io.Serializable;

/**
 * @author Administrator
 * @date 2017/3/14
 */
public class Header implements Serializable {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型名称
     */
    private String fieldType;

    private String fieldLength;

    public Header(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public Header() {
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(String fieldLength) {
        this.fieldLength = fieldLength;
    }
}
