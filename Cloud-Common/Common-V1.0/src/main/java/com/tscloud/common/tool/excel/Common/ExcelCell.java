package com.tscloud.common.tool.excel.Common;


import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jiancai.wang on 2017/5/27.
 */
public class ExcelCell {

    public enum ExcelCellType {

        BOOLEAN(Boolean.class.getName()),
        INT(Integer.class.getName()),
        LONG(Long.class.getName()),
        FLOAT(Float.class.getName()),
        DOUBLE(Double.class.getName()),
        STRING(String.class.getName()),
        DATE(Date.class.getName());

        String type;

        ExcelCellType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public static ExcelCellType getCellType(String fieldType) {
            switch (fieldType) {
                case "java.lang.Boolean":
                case "BOOLEAN":
                case "BOOL":
                    return BOOLEAN;
                case "java.lang.Integer":
                case "INTEGER":
                case "INT":
                    return INT;
                case "java.lang.Long":
                case "LONG":
                case "TIMESTAMP":
                    return LONG;
                case "java.lang.Float":
                case "FLOAT":
                    return FLOAT;
                case "java.lang.Double":
                case "DOUBLE":
                    return DOUBLE;
                case "java.lang.String":
                case "STRING":
                case "VARCHAR":
                    return STRING;
                case "java.util.Date":
                case "DATE":
                case "TIME":
                    return DATE;
                default:
                    throw new IllegalArgumentException("Can't support argument type '" + fieldType + "'. required " +
                            Stream.of(values()).map(ExcelCellType::toString).collect(Collectors.joining(", ", "[", "]")));
            }
        }

        @Override
        public String toString() {
            return type;
        }
    }

    private String field;
    private ExcelCellType type;
    private Object value;


    public ExcelCell(String field, ExcelCellType excelCellType) {
        this.field = field;
        this.type = excelCellType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public ExcelCellType getType() {
        return type;
    }

    public void setType(ExcelCellType type) {
        this.type = type;
    }
}
