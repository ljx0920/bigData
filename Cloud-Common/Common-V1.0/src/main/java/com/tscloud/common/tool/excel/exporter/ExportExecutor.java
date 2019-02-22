package com.tscloud.common.tool.excel.exporter;

import com.tscloud.common.tool.excel.Common.ExcelContent;
import com.tscloud.common.tool.excel.exception.ExportException;
import com.tscloud.common.tool.excel.exporter.impl.ExcelExporter;
import com.tscloud.common.tool.workflow.DataPackage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author jiancai.wang
 * @date 2017/5/31
 */
public class ExportExecutor {

    private static final Logger log = LogManager.getLogger(ExportExecutor.class);

    private static void executor(String filePath, ExportConfigure configure, ExcelContent content) throws ExportException {
        switch (configure.getFileType()) {
            case EXCEL2007:
                new ExcelExporter().export(filePath, configure, content);
                break;
            case CSV:
                break;
            default:
                break;
        }
    }

    public static void executor(String filePath, DataPackage dataPackage) {
        try {
            executor(filePath, ExportConfigAdapter.transformConfig(dataPackage), ExportConfigAdapter.transformContent(dataPackage));
        } catch (ExportException e) {
            log.error("failed to export content. msg: " + e.getMessage());
        }
    }
}
