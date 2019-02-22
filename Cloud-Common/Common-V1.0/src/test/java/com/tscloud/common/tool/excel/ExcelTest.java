package com.tscloud.common.tool.excel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class ExcelTest {
    @Test
    public void test() throws Exception {
        long start = System.currentTimeMillis();

        List<List<Object>> table = new ExcelEventParser(new FileInputStream(new File("c:/tmp/test2.xls")), ".xls", 20).parse().get(0);

        long end = System.currentTimeMillis();

        System.out.println(table.get(0));
        System.out.println(table.size());
        System.out.println(end - start);
    }
}
