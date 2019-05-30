package com.cloud.appmanage.util;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * httpRequest 请求工具类
 *
 * @author yulj
 * @create: 2019/01/26 2:01
 */
public class HttpRequestUtil {

    public static HttpEntity<String> getRequestEntity(Map<String, Object> dataMap) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<String>(new Gson().toJson(dataMap), requestHeaders);
    }

    public static HttpEntity<String> getRequestEntity(String jsonStr) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return new HttpEntity<String>(jsonStr, requestHeaders);
    }
}
