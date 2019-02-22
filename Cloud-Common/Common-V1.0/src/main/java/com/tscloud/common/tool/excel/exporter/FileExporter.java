package com.tscloud.common.tool.excel.exporter;

import com.tscloud.common.tool.excel.Common.ExcelContent;
import com.tscloud.common.tool.excel.exception.ExportException;
import com.tscloud.common.tool.hadooptool.hdfs.FileExistsException;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jiancai.wang on 2017/5/31.
 */
public abstract class FileExporter {

    public void export(String outputPath, ExportConfigure exportConfigure, ExcelContent excelContent) throws ExportException {
        try {

            Objects.requireNonNull(outputPath, "Please point outpoint file path.");
            Objects.requireNonNull(excelContent.getData(),"export data is empty.");
            Objects.requireNonNull(exportConfigure.getFileType(), "Please point output file type, required " +
                    Stream.of(ExportConfigure.ExportType.values()).map(ExportConfigure.ExportType::toString).collect(Collectors.joining(", ", "[", "]")));
            File output = new File(outputPath);
            if (output.exists() || output.isDirectory()) {
                throw new FileExistsException("can't create file, file is existed or is a dir. ");
            }
            OutputStream outputStream = new FileOutputStream(outputPath);
            exportContent(excelContent, outputStream);
        } catch (Exception e) {
            throw new ExportException("error in export file, case: " + e.getMessage());
        }
    }

    abstract protected void exportContent(ExcelContent excelContent, OutputStream outputStream) throws ExportException;
}
