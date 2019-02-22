package com.tscloud.common.utils;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.DateUtil;

import java.util.List;

/**
 * 利用POI操作Excel文档的工具类
 *
 * @author yifan.zhang
 * @date 2017/9/17
 */
public class ExcelUtil {
    /**
     * @param wb
     * @param sheetIndex
     * @return
     * @description 得到Excel中的所有数据
     */
    public static List<String[]> getAllData(Workbook wb, int sheetIndex) {
        List<String[]> dataList = Lists.newArrayListWithCapacity(100);
        int columnNum = 0;
        Sheet sheet = wb.getSheetAt(sheetIndex);
        if (sheet.getRow(0) != null) {
            columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
        }
        if (columnNum > 0) {
            for (Row row : sheet) {
                String[] singleRow = new String[columnNum];
                int n = 0;
                for (int i = 0; i < columnNum; i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    switch (cell.getCellTypeEnum()) {
                        case BLANK:
                            singleRow[n] = "";
                            break;
                        case BOOLEAN:
                            singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
                            break;
                        //数值
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                singleRow[n] = String.valueOf(cell.getDateCellValue());
                            } else {
                                cell.setCellType(CellType.STRING);
                                String temp = cell.getStringCellValue();
                                //判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                                if (temp.contains(".")) {
                                    singleRow[n] = String.valueOf(new Double(temp)).trim();
                                } else {
                                    singleRow[n] = temp.trim();
                                }
                            }
                            break;
                        case STRING:
                            singleRow[n] = cell.getStringCellValue().trim();
                            break;
                        case ERROR:
                            singleRow[n] = "";
                            break;
                        case FORMULA:
                            cell.setCellType(CellType.STRING);
                            singleRow[n] = cell.getStringCellValue();
                            if (singleRow[n] != null) {
                                singleRow[n] = singleRow[n].replaceAll("#N/A", "").trim();
                            }
                            break;
                        default:
                            singleRow[n] = "";
                            break;
                    }
                    n++;
                }
                //if("".equals(singleRow[0])){continue;}//如果第一行为空，跳过
                dataList.add(singleRow);
            }
        }
        return dataList;
    }

    public static Workbook exportExcel(List<String[]> list, String[] headers, int sheetSize) {
        Workbook wb = new HSSFWorkbook();
        int sheetNum = list.size() / sheetSize + 1;
        for (int sheetNo = 0; sheetNo < sheetNum; sheetNo++) {
            int rowIndex = 0;
            Sheet sheet = wb.createSheet();
            Row row = sheet.createRow(rowIndex++);
            //表头
            for (int i = 0; i < headers.length; i++) {
                row.createCell(i).setCellValue(headers[i]);
            }
            for (int i1 = sheetSize * sheetNo; i1 < Math.min(list.size(), sheetSize * (sheetNo + 1)); i1++) {
                String[] aList = list.get(i1);
                row = sheet.createRow(rowIndex++);
                for (int i = 0; i < headers.length; i++) {
                    row.createCell(i).setCellValue(aList[i]);
                }
            }
        }
        return wb;
    }
}
