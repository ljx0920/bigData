package com.tscloud.common.tool.jobtool;

import org.junit.Test;

/**
 * Created by admin on 2015/4/5.
 */
public class JobToolTest {

    @Test
    public void test() {
        try {
            JobTool.getInstance().addJob("JobTest","JobTest", "0/5 * * * * ?", JobTestDemo.class);
            while (true) {
                Thread.sleep(1 * 1000 * 2);
                System.out.println("--------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
