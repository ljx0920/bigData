package com.tscloud.common.tool.excel.exporter.impl;

import com.tscloud.common.tool.excel.Common.ExcelCell;
import com.tscloud.common.tool.excel.Common.ExcelContent;
import com.tscloud.common.tool.excel.exception.ExportException;
import com.tscloud.common.tool.excel.exporter.FileExporter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by jiancai.wang on 2017/5/27.
 */
public class ExcelExporter extends FileExporter {


    @Override
    protected void exportContent(ExcelContent excelContent, OutputStream outputStream) throws ExportException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(Objects.isNull(excelContent.getSheetName()) ? "sheet1" : excelContent.getSheetName());
        try {
            // 表头
            createTitle(sheet, 0, excelContent.getHeader());
            // 表内容
            for (int j = 0; j < excelContent.getData().size(); j++) {
                createRow(sheet, j + 1, excelContent.getHeader(), excelContent.getData().get(j));
            }
            workbook.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new ExportException("failed to export data." + e);
        }
    }

    private void createTitle(Sheet sheet, int rowNum, List<ExcelCell> headerList) {
        Row row = sheet.createRow(rowNum);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headerList.get(i).getField());
        }
    }

    private void createRow(Sheet sheet, int rowNum, List<ExcelCell> headers, Map<String, Object> rowData) {
        Row row = sheet.createRow(rowNum);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = row.createCell(i);
            setValue(cell, headers.get(i).getType().getType(), rowData.get(headers.get(i).getField()));
        }
    }

    private void setValue(Cell cell, String type, Object value) {
//        switch (type) {
//            case "java.lang.Boolean":
//                cell.setCellValue((Boolean) value);
//                break;
//            case "java.lang.Integer":
//                cell.setCellValue((Double) value);
//                break;
//            case "java.lang.Float":
//                cell.setCellValue((Double) value);
//                break;
//            case "java.lang.Long":
//                cell.setCellValue((Double) value);
//                break;
//            case "java.lang.Double":
//                cell.setCellValue((Double) value);
//                break;
//            case "java.util.Date":
//                cell.setCellValue((Date) value);
//                break;
//            default:
//                break;
//        }
        cell.setCellValue(value.toString());
    }
}
