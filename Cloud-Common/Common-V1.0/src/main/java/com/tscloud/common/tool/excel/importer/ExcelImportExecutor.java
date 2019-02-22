package com.tscloud.common.tool.excel.importer;

import com.google.common.collect.Lists;
import com.tscloud.common.tool.excel.Common.ExcelContent;
import com.tscloud.common.tool.excel.exception.ImportException;
import com.tscloud.common.tool.workflow.DataPackage;
import com.tscloud.common.tool.workflow.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;

/**
 * Created by jiancai.wang on 2017/5/31.
 */
public class ExcelImportExecutor {

    private static final Logger log = LogManager.getLogger(ExcelImportExecutor.class);

    private static ExcelContent executor(String inputExcelPath, ExcelImportConfig configure) throws ImportException {
        return new ExcelImporter().importExcel(inputExcelPath, configure);
    }

    public static DataPackage executor(String filePath, Integer startNum, DataPackage dataPackage) {
        try {
            dataPackage.setDataList(executor(filePath, ExcelImportConfigAdapter.transformConfig(dataPackage).setStartRowNum(startNum)).getDataValue());
        } catch (ImportException e) {
            log.error("failed import excel, msg: " + e.getMessage());
        }
        return dataPackage;
    }

    @Deprecated
    public static List<DataPackage> executor(String filePath, Integer startNum, Integer packageSize, DataPackage dataPackage) {
        List<DataPackage> packageList = null;
        try {
            packageList = ExcelImportConfigAdapter.transformPackage(executor(filePath, ExcelImportConfigAdapter.transformConfig(dataPackage).setStartRowNum(startNum).setPageSize(packageSize)));
        } catch (ImportException e) {
            log.error("failed import excel, msg: " + e.getMessage());
        }
        return packageList;
    }

    public static List<DataPackage> execute(InputStream inputStream, String suffix, int startNum, int packageSize, List<Header> headers) {
        List<DataPackage> packageList;
        try {
            ExcelImporter excelImporter = new ExcelImporter();
            ExcelContent excelContent = excelImporter.importExcelFromStream(inputStream,
                    ExcelImportConfigAdapter.transformConfig(headers)
                            .setStartRowNum(startNum)
                            .setPageSize(packageSize),
                    suffix);

            packageList = ExcelImportConfigAdapter.transformPackage(excelContent);
        } catch (ImportException e) {
            log.error("failed import excel, msg: " + e.toString());
            packageList = Lists.newArrayList();
        }
        return packageList;
    }

    /**
     * 获取文件后缀(xlsx或xls)
     *
     * @param strFileName
     * @return
     */
    public static String getExcelSuffix(String strFileName) {
        if (strFileName.endsWith(".xlsx")) {
            return ".xlsx";
        } else if (strFileName.endsWith(".xls")) {
            return ".xls";
        } else {
            // 目前暂时将其它类型扩展名的文件强制按照xls格式进行处理
            return ".xls";
        }
    }
}
