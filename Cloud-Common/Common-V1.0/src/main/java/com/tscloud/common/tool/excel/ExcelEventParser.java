package com.tscloud.common.tool.excel;

import com.google.common.collect.Lists;
import com.tscloud.common.utils.FileUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.*;

/**
 * Excel解析
 */
public class ExcelEventParser {

    private static final Logger log = LogManager.getLogger(ExcelEventParser.class);

    private InputStream is;
    private String suffix;
    private int headerSize;

    public ExcelEventParser(InputStream is, String suffix, int headerSize) {
        this.is = is;
        this.suffix = suffix;
        this.headerSize = headerSize;
    }

    /**
     * 将ftp文件流转成本地流
     *
     * @param inputStream
     * @param fileName
     * @return
     * @throws Exception
     */
    private static String saveTempFile(InputStream inputStream, String fileName) throws Exception {
        String path = "./temp/ftpfile/";
        File file = new File(path);
        try {
            if (!file.exists()) {
                file.mkdirs();
            }
            path = file.getPath() + "/" + fileName;
            int index;
            byte[] bytes = new byte[1024];
            FileOutputStream downloadFile = new FileOutputStream(path);
            while ((index = inputStream.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }
            downloadFile.close();
            inputStream.close();
        } catch (Exception e) {
            log.error("[FTPFileTool] Excel下载到本地失败！,[Path:" + path + "]", e);
            throw new Exception(e);
        }

        log.info("processed file length: {} --> {}", path, new File(path).length());
        return path;
    }

    public List<List<List<Object>>> parse() throws Exception {
        List<List<List<Object>>> result = new LinkedList<>();
        String filePath = saveTempFile(is, UUID.randomUUID().toString() + suffix);

        try {
            if (".xls".equalsIgnoreCase(suffix)) {
                parseXls(result, filePath);
            } else if (".xlsx".equalsIgnoreCase(suffix)) {
                parseXlsx(result, filePath);
            } else {
                throw new IllegalArgumentException("unsupport file type : " + suffix);
            }
        } finally {
            FileUtil.delFile(filePath);
        }

        return result;
    }

    private void parseXlsx(List<List<List<Object>>> result, String filePath) {
        OPCPackage pkg = null;

        try {
            pkg = OPCPackage.open(filePath, PackageAccess.READ);
            XSSFReader xssfReader = new XSSFReader(pkg);

            StylesTable styles = xssfReader.getStylesTable();
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
            Iterator<InputStream> it = xssfReader.getSheetsData();
            int i = 0;
            while (it.hasNext()) {
                InputStream sheetInputStream = it.next();
                try {
                    log.info("begin processing sheet {}", i);
                    processSheet(styles, strings, sheetInputStream, result);
                    log.info("after processing sheet {}", i);
                    i++;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    sheetInputStream.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (pkg != null) {
                try {
                    pkg.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void parseXls(List<List<List<Object>>> result, String filePath) {
        try {
            File file = new File(filePath);
            NPOIFSFileSystem fs = new NPOIFSFileSystem(new FileInputStream(file));
            Workbook workbook = new HSSFWorkbook(fs.getRoot(), true);
            Iterator<Sheet> it = workbook.sheetIterator();
            while (it.hasNext()) {
                Sheet sheet = it.next();
                int totalRow = sheet.getLastRowNum() + 1;
                List<List<Object>> dataList = new LinkedList<>();
                for (int i = 0; i < totalRow; i++) {
                    dataList.add(readRow(sheet, i));
                }

                result.add(dataList);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Object> readRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return new ArrayList<>();
        }

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

    private void processSheet(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream sheetInputStream, List<List<List<Object>>> result) throws ParserConfigurationException, SAXException, IOException {
        XMLReader sheetParser = SAXHelper.newXMLReader();
        List<List<Object>> sheetData = new LinkedList<>();
        sheetParser.setContentHandler(new XSSFSheetXMLHandler(styles, strings, new SimpleSheetContentsHandler(sheetData, headerSize), false));
        sheetParser.parse(new InputSource(sheetInputStream));

        result.add(sheetData);
    }

    public static class SimpleSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
        private final List<List<Object>> sheetData;
        private int headerSize;

        private int currentRow = -1;
        private int currentCol = -1;
        protected List<Object> row;

        public SimpleSheetContentsHandler(List<List<Object>> sheetData, int headerSize) {
            this.sheetData = sheetData;
            this.headerSize = headerSize;
        }

        @Override
        public void startRow(int rowNum) {
            // 如果有空行
            if (rowNum - currentRow > 1) {
                for (int i = 0; i < rowNum - currentRow - 1; i++) {
                    List<Object> emptyRow = new LinkedList<>();
                    for (int j = 0; j < headerSize; j++) {
                        emptyRow.add("");
                    }
                    sheetData.add(emptyRow);
                }
            }
            row = new LinkedList<>();
            currentRow = rowNum;
            currentCol = -1;
        }

        @Override
        public void endRow(int rowNum) {
            // 不足header列数，补空串
            if (row.size() < headerSize) {
                int diff = headerSize - row.size();
                for (int i = 0; i < diff; i++) {
                    row.add("");
                }
            }
            sheetData.add(row);
        }

        @Override
        public void cell(String cellReference, String formattedValue, XSSFComment comment) {
            // 忽略超过header的列
            if (row.size() < headerSize) {
                if (cellReference == null) {
                    cellReference = new CellAddress(currentRow, currentCol).formatAsString();
                }

                int thisCol = new CellReference(cellReference).getCol();
                // 如果存在空列，补空串
                int missedCols = thisCol - currentCol - 1;
                for (int i = 0; i < missedCols; i++) {
                    row.add("");
                }
                currentCol = thisCol;

                row.add(formattedValue);
            }
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {

        }
    }
}
