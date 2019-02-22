package com.tscloud.common.utils;

import org.junit.Test;

public class HttpUtilTest {

    @Test
    public void doGet() {
        String url = "http://localhost:9002/restful/cloud/da/diagram/getInfo";

        String doGet = HttpUtils.doGet(url);
        System.out.println(doGet);
    }

    @Test
    public void doPost() {
        String url = "http://localhost:9002/restful/cloud/da/diagram/getAllModels";
        String param = "{\"diagramId\":23}";
        String doPost = HttpUtils.doPost(url, param);
        System.out.println(doPost);
    }
}
