package com.tscloud.common.tool.excel.importer;

import com.google.common.collect.Lists;
import com.tscloud.common.tool.excel.Common.ExcelCell;
import com.tscloud.common.tool.excel.Common.ExcelContent;
import com.tscloud.common.tool.workflow.DataPackage;
import com.tscloud.common.tool.workflow.Header;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jiancai.wang on 2017/5/31.
 * 导入配置是配置：生成导入可用的配置类
 * 1. 平台数据包配置类
 * 2. xml
 * 3. json
 * 等其他的配置方式，进行适配
 */
public class ExcelImportConfigAdapter {

    public static ExcelImportConfig transformConfig(DataPackage dataPackage) {
        Objects.requireNonNull(dataPackage.getHeaderList(), "error dataPackage, header list is null.");
        List<ExcelCell> header = dataPackage.getHeaderList().stream()
                .map(h -> new ExcelCell(h.getFieldName(), ExcelCell.ExcelCellType.getCellType(h.getFieldType())))
                .collect(Collectors.toList());
        return new ExcelImportConfig().setHeader(header);
    }

    public static ExcelImportConfig transformConfig(List<Header> headers) {
        List<ExcelCell> excelCells = headers.stream()
                .map(h -> new ExcelCell(h.getFieldName(), ExcelCell.ExcelCellType.getCellType(h.getFieldType())))
                .collect(Collectors.toList());
        return new ExcelImportConfig().setHeader(excelCells);
    }

    @Deprecated
    public static List<DataPackage> transformPackageOld(ExcelContent excelContent) {
        List<DataPackage> packageList = Lists.newLinkedList();
        int packageSize = excelContent.getPackageSize();
        List<Object> dataValues = excelContent.getDataValue();
        int dataSize = dataValues.size();
        final List<Header> header = excelContent.getHeader().stream()
                .map(h -> new Header(h.getField(), h.getType().getType()))
                .collect(Collectors.toList());
        for (int i = 0; i < dataSize / packageSize; i++) {
            int startIndex = i * packageSize;
            int endIndex = i == dataSize / packageSize - 1 ? dataSize : packageSize * (i + 1);
            DataPackage dataPackage = new DataPackage();
            dataPackage.setName(excelContent.getSheetName());
            dataPackage.setHeaderList(header);
            dataPackage.setDataList(dataValues.subList(startIndex, endIndex));
            packageList.add(dataPackage);
        }
        return packageList;
    }

    public static List<DataPackage> transformPackage(ExcelContent excelContent) {
        List<DataPackage> packageList = Lists.newLinkedList();
        int packageSize = excelContent.getPackageSize();
        List<Object> dataValues = excelContent.getDataValue();
        int dataSize = dataValues.size();
        if (0 == dataSize) {
            return packageList;
        }

        final List<Header> header = excelContent.getHeader().stream()
                .map(h -> new Header(h.getField(), h.getType().getType()))
                .collect(Collectors.toList());

        // 按照packageSize进行分包;
        DataPackage varDataPackage = null;
        List<Object> varDataList = null;
        for (int i = 0; i < dataValues.size(); i++) {
            if (i % packageSize == 0 && i > 0) {
                varDataPackage.setDataList(varDataList);
                packageList.add(varDataPackage);
                varDataPackage = null;
            }

            if (null == varDataPackage) {
                varDataPackage = new DataPackage();
                varDataPackage.setName(excelContent.getSheetName());
                varDataPackage.setHeaderList(header);
                varDataList = Lists.newArrayList();
            }
            varDataList.add(dataValues.get(i));
        }

        if (null != varDataList && varDataList.size() > 0) {
            varDataPackage.setDataList(varDataList);
            packageList.add(varDataPackage);
        }

        return packageList;
    }
}
