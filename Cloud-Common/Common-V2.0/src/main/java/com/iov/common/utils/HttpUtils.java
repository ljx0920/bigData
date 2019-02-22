package com.iov.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * http工具类
 *
 * @author yifan.zhang
 * @date 2017/9/25
 */
public class HttpUtils {

    private static final Logger log = LogManager.getLogger(HttpUtils.class);

    /**
     * get请求
     *
     * @param url rest请求url地址
     * @return
     */
    public static String doGet(String url) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpclient.execute(request);

            //请求发送成功，并得到响应
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                HttpEntity entity = response.getEntity();
                String strResult = EntityUtils.toString(entity);
                log.info("[HttpUtils] doGet success, url:{}, response:{}", url, strResult);
                return strResult;
            } else {
                log.error("[HttpUtils] doGet error, url:{}, state:{}", url, statusCode);
            }
        } catch (ClientProtocolException e) {
            log.error("[HttpUtils] doGet http protocol error, url:{}", url, e);
        } catch (IOException e) {
            log.error("[HttpUtils] doGet IO error, url:{}", url, e);
        }
        return null;
    }

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url rest请求url地址
     * @param jsonParams json格式参数（以String形式传递）
     * @return
     */
    public static String doPost(String url, String jsonParams) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(jsonParams, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String strResult = EntityUtils.toString(responseEntity);
                log.info("[HttpUtils] doPost success, url:{}, response:{}", url, strResult);
                return strResult;
            } else {
                log.error("[HttpUtils] doPost error, url:{}, state:{}", url, state);
            }
        } catch (ClientProtocolException e) {
            log.error("[HttpUtils] doPost http protocol error, url:{}", url, e);
        } catch (IOException e) {
            log.error("[HttpUtils] doPost IO error, url:{}", url, e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                log.error("[HttpUtils] doPost close client error!", e);
            }
        }
        return null;
    }
}
