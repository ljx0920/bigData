package com.tscloud.common.tool.excel;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Excel解析
 */
public class ExcelParser {

    private static final Logger LOG = LogManager.getLogger(ExcelParser.class);

    private Workbook workbook;
    private int rowSize = 0;

    public ExcelParser(InputStream inputStream, String suffix) throws Exception {
        try {
            if(".xls".equals(suffix)){
                NPOIFSFileSystem fs = new NPOIFSFileSystem(inputStream);
                workbook = new HSSFWorkbook(fs.getRoot(), true);
                LOG.info("--------------------------读取成功！");
            }else if(".xlsx".equals(suffix)){
                ZipSecureFile.setMinInflateRatio(0.009);
//                OPCPackage pkg = OPCPackage.open(inputStream);
                workbook = new XSSFWorkbook(inputStream);
//                workbook = new XSSFWorkbook(inputStream);
            }else{
                throw new IllegalArgumentException("unsupport file type : " + suffix);
            }
        } catch (Exception e) {
            LOG.error("--------------------------读取失败！");
            LOG.error("failed to parse excel." + e.getMessage());
            throw e;
        }
    }


    /**
     * 获取指定sheet的数据行数
     *
     * @return
     */
    public int getRowSize(int sheetNum) {
        Sheet sheet = workbook.getSheetAt(sheetNum);
        rowSize += sheet.getLastRowNum() + 1;
        return this.rowSize;
    }

    /**
     * 读取Excel的一行数据
     *
     * @param sheetNum
     * @param rowNum
     * @return
     */
    public List<Object> readRow(int sheetNum, int rowNum) {
        Sheet sheet = workbook.getSheetAt(sheetNum);
        Row row = sheet.getRow(rowNum);
        int cellSize = row.getLastCellNum();
        List<Object> list = Lists.newArrayList();
        for (int i = 0; i < cellSize; i++) {
            Cell cell = row.getCell(i);
            if (cell == null) {
                list.add(null);
            } else {
                switch (cell.getCellTypeEnum()) {
                    case NUMERIC:
                        list.add(cell.getNumericCellValue());
                        break;
                    case BOOLEAN:
                        list.add(cell.getBooleanCellValue());
                        break;
                    case ERROR:
                        list.add(cell.getErrorCellValue());
                        break;
                    case _NONE:
                    case FORMULA:
                    case BLANK:
                    case STRING:
                        list.add(cell.getStringCellValue());
                        break;
                    default:
                        list.add(cell.getStringCellValue());
                }
            }
        }
        return list;
    }

    /**
     *读取所以的sheet
     * @return
     */
    public Iterator<Sheet> getSheet(){
        return workbook.sheetIterator();
    }



    public static void main(String[] rg) {
        try {
            InputStream inputStream = new FileInputStream(new File("D:\\home\\emp.xls"));
            ExcelParser excelParser = new ExcelParser(inputStream, ".xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
