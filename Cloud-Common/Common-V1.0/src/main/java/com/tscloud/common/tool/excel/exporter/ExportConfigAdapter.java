package com.tscloud.common.tool.excel.exporter;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tscloud.common.tool.excel.Common.ExcelCell;
import com.tscloud.common.tool.excel.Common.ExcelContent;
import com.tscloud.common.tool.workflow.DataPackage;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jiancai.wang on 2017/5/31.
 */
public class ExportConfigAdapter {

    private static final ExportConfigure.ExportType DEFAULT_EXPORT_TYPE = ExportConfigure.ExportType.EXCEL2007;

    public static ExportConfigure transformConfig(DataPackage dataPackage) {
        ExportConfigure.ExportType type = Objects.isNull(dataPackage.getDataType()) ? DEFAULT_EXPORT_TYPE : ExportConfigure.ExportType.getExportType(dataPackage.getDataType());
        return new ExportConfigure().setFileType(type);
    }

    public static ExcelContent transformContent(DataPackage dataPackage) {
        ExcelContent content = new ExcelContent();
        List<ExcelCell> header = dataPackage.getHeaderList().stream()
                .map(h -> new ExcelCell(h.getFieldName(), ExcelCell.ExcelCellType.getCellType(h.getFieldType())))
                .collect(Collectors.toList());

        List<Map<String, Object>> sheet = Lists.newLinkedList();

        if (dataPackage.getDataList() != null) {
            for (int i = 0; i < dataPackage.getDataList().size(); i++) {
                Map<String, Object> row = Maps.newHashMap();
                Object[] dataList = dataPackage.getDataList().get(i).toString().split(",");
                for (int j = 0; j < dataPackage.getHeaderList().size(); j++) {
                    row.put(dataPackage.getHeaderList().get(j).getFieldName(), dataList[j]);
                }
                sheet.add(row);
            }
        }

        content.setHeader(header);
        content.setData(sheet);
        return content;
    }
}
