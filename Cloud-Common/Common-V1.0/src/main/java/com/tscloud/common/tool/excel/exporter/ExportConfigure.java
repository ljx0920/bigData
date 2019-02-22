package com.tscloud.common.tool.excel.exporter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jiancai.wang on 2017/5/31.
 */
public class ExportConfigure {

    enum ExportType {
        EXCEL2007("xlsx", "application/vnd.ms-excel"),
        CSV("csv", "application/csv");

        ExportType(String fileType, String contentType) {
            this.fileType = fileType;
            this.contentType = contentType;
        }

        String fileType;
        String contentType;

        @Override
        public String toString() {
            return "{" +
                    "fileType='" + fileType + '\'' +
                    ", contentType='" + contentType + '\'' +
                    '}';
        }

        public static ExportType getExportType(String exportType) {
            switch (exportType) {
                case "xlsx":
                    return EXCEL2007;
                case "excel":
                    return EXCEL2007;
                case "csv":
                    return CSV;
                default:
                    throw new IllegalArgumentException("Can't support argument type '" + exportType + "'. required " +
                            Stream.of(values()).map(ExportType::toString).collect(Collectors.joining(", ", "[", "]")));
            }
        }
    }

    // sheet name
    private String sheetName;
    // 导出文件类型
    private ExportType fileType;

    public String getSheetName() {
        return sheetName;
    }

    public ExportConfigure setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public ExportType getFileType() {
        return fileType;
    }

    public ExportConfigure setFileType(ExportType fileType) {
        this.fileType = fileType;
        return this;
    }
}
