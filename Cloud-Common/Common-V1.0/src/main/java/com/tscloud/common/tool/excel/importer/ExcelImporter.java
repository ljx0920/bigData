package com.tscloud.common.tool.excel.importer;

import com.google.common.collect.Lists;
import com.tscloud.common.tool.excel.Common.ExcelCell;
import com.tscloud.common.tool.excel.Common.ExcelContent;
import com.tscloud.common.tool.excel.exception.ImportException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by jiancai.wang on 2017/5/27.
 */
class ExcelImporter {

    private static final Logger log = LogManager.getLogger(ExcelImporter.class);

    ExcelContent importExcel(String inputExcelPath, ExcelImportConfig configure) throws ImportException {
        try {
            Objects.requireNonNull(configure.getHeader(), "No enough excel info, please config it.");
            Objects.requireNonNull(inputExcelPath, "Excel file path is null.");

            ExcelContent content = new ExcelContent();

            File file = new File(inputExcelPath);
            if (!file.exists() && file.isDirectory()) {
                throw new IllegalArgumentException("file dose not exist or is not a file. path: " + inputExcelPath);
            }
            String suffix = inputExcelPath.lastIndexOf(".") == -1 ? "" : inputExcelPath.substring(inputExcelPath.lastIndexOf("."));
            Workbook workbook = importExcelWorkBook(new FileInputStream(file), suffix);

            content.setHeader(configure.getHeader());
            content.setStartNum(configure.getStartRowNum());
            content.setPackageSize(configure.getPageSize());
            content.setData(readExcel(workbook, configure));

            return content;
        } catch (Exception e) {
            log.error("failed to import excel, cause: " + e.getMessage());
            throw new ImportException("failed to import excel. " + e);
        }
    }

    ExcelContent importExcelFromStream(InputStream inputStream, ExcelImportConfig configure, String suffix) throws ImportException {
        try {
            Objects.requireNonNull(configure.getHeader(), "No enough excel info, please config it.");
            Objects.requireNonNull(inputStream, "Excel file stream is null.");

            ExcelContent result = new ExcelContent();

            Workbook workbook = importExcelWorkBook(inputStream, suffix);

            result.setHeader(configure.getHeader());
            result.setStartNum(configure.getStartRowNum());
            result.setPackageSize(configure.getPageSize());
            result.setData(readExcel(workbook, configure));

            return result;
        } catch (Exception e) {
            log.error("failed to import excel, cause: " + e.getMessage());
            throw new ImportException("failed to import excel. " + e);
        }
    }

    private Workbook importExcelWorkBook(InputStream inputStream, String suffix) throws IOException {
        Workbook workbook;
        switch (suffix) {
            case ".xls":
                try {
                    workbook = new HSSFWorkbook(inputStream);
                } catch (IOException e) {
                    log.error("failed to parse excel." + e.getMessage());
                    throw e;
                }
                break;
            case ".xlsx":
                try {
                    workbook = new XSSFWorkbook(inputStream);
                } catch (IOException e) {
                    log.error("failed to parse excel." + e.getMessage());
                    throw e;
                }
                break;
            default:
                throw new IllegalArgumentException("unsupport file type. txt: " + suffix);
        }

        return workbook;
    }

    private List<Map<String, Object>> readExcel(Workbook workbook, ExcelImportConfig configure) {

        String sheetName = configure.getSheetName();
        List<ExcelCell> header = configure.getHeader();
        int startNum = configure.getStartRowNum();

        Sheet sheet = Objects.isNull(sheetName) ? workbook.getSheetAt(0) : workbook.getSheet(sheetName);

        if (Objects.isNull(sheet)) {
            throw new IllegalArgumentException("failed to find sheet or empty. sheet: " + sheetName);
        }

        List<Map<String, Object>> sheetData = Lists.newLinkedList();
        for (int i = startNum; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            Map<String, Object> rowMap = new LinkedHashMap<>();
            for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
                ExcelCell excelCell = header.get(j);
                Cell cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                // default data type is String
                String type = excelCell.getType().getType();
                switch (excelCell.getType()) {
                    case BOOLEAN:
                        rowMap.put(excelCell.getField(), getCellValue(cell, type));
                        break;
                    case INT:
                        rowMap.put(excelCell.getField(), getCellValue(cell, type));
                        break;
                    case LONG:
                        rowMap.put(excelCell.getField(), getCellValue(cell, type));
                        break;
                    case FLOAT:
                        rowMap.put(excelCell.getField(), getCellValue(cell, type));
                        break;
                    case DOUBLE:
                        rowMap.put(excelCell.getField(), getCellValue(cell, type));
                        break;
                    case STRING:
                        rowMap.put(excelCell.getField(), getCellValue(cell, type));
                        break;
                    case DATE:
                        rowMap.put(excelCell.getField(), getCellValue(cell, type));
                        break;
                    default:
                        break;
                }
            }
            sheetData.add(rowMap);
        }
        return sheetData;
    }

    private <T> T getCellValue(Cell cell, String type) {
        Object value = null;
        switch (cell.getCellTypeEnum()) {
            case BOOLEAN:
                if (Boolean.class.getName().equals(type)) {
                    value = cell.getBooleanCellValue();
                } else if (Integer.class.getName().equals(type)) {
                    value = cell.getBooleanCellValue() ? 1 : 0;
                } else if (String.class.getName().equals(type)) {
                    value = String.valueOf(cell.getBooleanCellValue());
                } else {
                    log.error("Wrong argument type. found: {}, required: {}", cell.getCellTypeEnum(), type);
                }
                break;
            case NUMERIC:
                if (Double.class.getName().equals(type)) {
                    value = cell.getNumericCellValue();
                } else if (Float.class.getName().equals(type)) {
                    value = new Double(cell.getNumericCellValue()).floatValue();
                } else if (Integer.class.getName().equals(type)) {
                    value = new Double(cell.getNumericCellValue()).intValue();
                } else if (Long.class.getName().equals(type)) {
                    value = new Double(cell.getNumericCellValue()).longValue();
                } else if (Date.class.getName().equals(type)) {
                    value = new Date(new Double(cell.getNumericCellValue()).longValue());
                } else if (String.class.getName().equals(type)) {
                    value = String.valueOf(cell.getNumericCellValue());
                } else {
                    log.error("Wrong argument type. found: {}, required: {}", cell.getCellTypeEnum(), type);
                }
                break;
            case STRING:
                if (Integer.class.getName().equals(type)) {
                    value = Integer.parseInt(cell.getStringCellValue());
                } else if (Long.class.getName().equals(type)) {
                    value = Long.parseLong(cell.getStringCellValue());
                } else if (Float.class.getName().equals(type)) {
                    value = Float.parseFloat(cell.getStringCellValue());
                } else if (Double.class.getName().equals(type)) {
                    value = Double.parseDouble(cell.getStringCellValue());
                } else if (String.class.getName().equals(type)) {
                    value = String.valueOf(cell.getStringCellValue());
                } else {
                    log.error("Wrong argument type. found: {}, required: {}", cell.getCellTypeEnum(), type);
                }
                break;
            case BLANK:
                break;
            default:
                log.error("Wrong argument type. found: {}, required: {}", cell.getCellTypeEnum(), type);
                break;
        }
        return (T) value;
    }
}
