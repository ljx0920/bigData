package com.tscloud.common.tool.excel.importer;

import com.tscloud.common.tool.excel.Common.ExcelCell;

import java.util.List;

/**
 * Created by jiancai.wang on 2017/5/27.
 */
public class ExcelImportConfig {

    // sheet name
    private String sheetName;
    // 表头
    private List<ExcelCell> header;
    // 起始行
    private int startRowNum;
    // 分页大小
    private int pageSize;


    public String getSheetName() {
        return sheetName;
    }

    public ExcelImportConfig setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public List<ExcelCell> getHeader() {
        return header;
    }

    public ExcelImportConfig setHeader(List<ExcelCell> header) {
        this.header = header;
        return this;
    }

    public int getStartRowNum() {
        return startRowNum;
    }

    public ExcelImportConfig setStartRowNum(int startRowNum) {
        this.startRowNum = startRowNum;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public ExcelImportConfig setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}
