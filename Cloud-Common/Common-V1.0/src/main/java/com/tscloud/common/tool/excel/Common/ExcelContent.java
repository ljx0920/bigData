package com.tscloud.common.tool.excel.Common;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by jiancai.wang on 2017/5/27.
 */
public class ExcelContent {

    //
    String sheetName;
    // excel size
    private int startNum;
    // 分包数据大小
    private int packageSize;
    // 表头
    private List<ExcelCell> header;
    // 数据
    private List<Map<String, Object>> data;

    public String getSheetName() {
        return sheetName;
    }

    public ExcelContent setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public List<ExcelCell> getHeader() {
        return header;
    }

    public ExcelContent setHeader(List<ExcelCell> header) {
        this.header = header;
        return this;
    }

    public int getStartNum() {
        return startNum;
    }

    public ExcelContent setStartNum(int startNum) {
        this.startNum = startNum;
        return this;
    }

    public int getPackageSize() {
        return packageSize;
    }

    public ExcelContent setPackageSize(int packageSize) {
        this.packageSize = packageSize;
        return this;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public ExcelContent setData(List<Map<String, Object>> data) {
        this.data = data;
        return this;
    }

    public List<String> getDataValueOld() {
        List<String> result = Lists.newArrayList();
        if (data.isEmpty()) {
            return result;
        }
        Function<Map<String, Object>, String> formatData = (map) -> {
            StringBuilder builder = new StringBuilder();
            for (Object o : map.values()) {
                builder.append(o).append("\n");
            }
            return builder.toString();
        };
        return data.stream().map(formatData).collect(Collectors.toList());
    }

    public List<Object> getDataValue() {
        List<Object> result = Lists.newArrayList();
        if (data.isEmpty()) {
            return result;
        }
        Function<Map<String, Object>, Object> formatData = (map) -> {
            List<Object> rawData = Lists.newArrayList();
            rawData.addAll(map.values());
            return rawData;
        };
        return data.stream().map(formatData).collect(Collectors.toList());
    }
}
