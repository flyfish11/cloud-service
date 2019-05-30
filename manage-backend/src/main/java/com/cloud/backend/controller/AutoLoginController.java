package com.cloud.backend.controller;


import com.cloud.backend.utils.EHCacheUtil;
import com.cloud.common.utils.DateUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieOrigin;
import org.apache.http.cookie.MalformedCookieException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.DefaultCookieSpec;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/autoLogin")
public class AutoLoginController {


    @ApiOperation(value = "用户的菜单", notes = "当前登录用户的菜单")
    @ApiImplicitParam(name = "application", value = "查询参数", required = false, dataType = "Application ", paramType = "query")
    @RequestMapping("/login")
    public void autoLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String temhtml = (String) EHCacheUtil.getValue("html","loginHtml");
        if (!EHCacheUtil.contains("html","loginHtml")) {
            InputStreamReader inputStreamReader = null;
            BufferedReader reader = null;
            String line = null;
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getParam = new HttpGet("http://api.gateway.com");
            CloseableHttpResponse response1 = httpClient.execute(getParam);
            InputStream inputStream1 = response1.getEntity().getContent();
            inputStreamReader = new InputStreamReader(inputStream1);
            reader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("href=\"", "href=\"http://api.gateway.com");
                line = line.replaceAll("src=\"", "src=\"http://api.gateway.com");
                if (line.contains("//]]>")) {
                    System.out.println(line);
                    line = line + "\nwindow.onload = function(){\n" +
                            "document.getElementById(\"user_login\").value='root'\n" +
                            "document.getElementById('user_password').value=\"hyhh2018\";\n" +
                            "document.getElementById('new_user').submit();\n" +
                            "}";
                }
                if (line.contains("action=\"/users/sign_in\"")) {
                    line = line.replace("action=\"/users/sign_in\"", "action=\"http://api.gateway.com/users/sign_in\"");
                }
                stringBuffer.append(line);
            }
//                Document document = Jsoup.parse(stringBuffer.toString());
//                PrintWriter out=response.getWriter();
//                out.println(document.outerHtml());
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Access-Control-Expose-Headers", "Cookie");
            Header[] headers = response1.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                Header header = headers[i];
                if (header.getName().equals("Set-Cookie")) {
                    response.setHeader("Cookie", header.getValue().split(";")[0]);
                    response.setHeader("originCookie", request.getHeader("Cookie"));
                }
            }
            EHCacheUtil.put("html", "loginHtml", stringBuffer.toString());
        } else {
            String html = String.valueOf(EHCacheUtil.getValue("html", "loginHtml"));
            Document document = Jsoup.parse(html);
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("originCookie", request.getHeader("Cookie"));
            EHCacheUtil.remove("html", "loginHtml");
            PrintWriter out = response.getWriter();
            out.println(document.outerHtml());
        }

    }

    @RequestMapping("/newPage")
    public void newHtml(HttpServletResponse response) throws Exception {
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        String line = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getParam = new HttpGet("http://api.gateway.com");
        CloseableHttpResponse response1 = httpClient.execute(getParam);
        InputStream inputStream1 = response1.getEntity().getContent();
        inputStreamReader = new InputStreamReader(inputStream1);
        reader = new BufferedReader(inputStreamReader);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//此处可以用Stringbuffer等接收
        byte[] b = new byte[1024];
        int len = 0;
        while (true) {
            len = inputStream1.read(b);
            if (len == -1) {
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        System.out.println(byteArrayOutputStream.toString());
        Document doc = Jsoup.parse(byteArrayOutputStream.toString());
        Elements formElement = doc.getElementsByAttributeValue("type", "hidden");
        String s = formElement.get(0).attr("value");
        String ss = formElement.get(1).attr("value");
        reader.close();
        inputStreamReader.close();
        inputStream1.close();
        //post 请求是登录操作
        HttpPost dologin = new HttpPost("http://api.gateway.com/users/sign_in");
        //httpClient.setParams();
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("authenticity_token", ss));
        list.add(new BasicNameValuePair("utf8", s));
        list.add(new BasicNameValuePair("user[login]", "root"));
        list.add(new BasicNameValuePair("user[password]", "hyhh2018"));
        list.add(new BasicNameValuePair("user[remember_me]", "0"));
        UrlEncodedFormEntity urlEncodedFormEntity = null;
        urlEncodedFormEntity = new UrlEncodedFormEntity(list);
        dologin.setEntity(urlEncodedFormEntity);
        Header[] headers = response1.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            Header header = headers[i];
            if (header.getName().equals("Set-Cookie")) {
                dologin.setHeader("Cookie", header.getValue());
            }
        }
        CloseableHttpResponse response2 = httpClient.execute(dologin);
        InputStream inputStream2 = response2.getEntity().getContent();
        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();//此处可以用Stringbuffer等接收
        byte[] b1 = new byte[1024];
        int len1 = 0;
        while (true) {
            len1 = inputStream2.read(b1);
            if (len1 == -1) {
                break;
            }
            byteArrayOutputStream1.write(b1, 0, len1);
        }
        System.out.println(byteArrayOutputStream1.toString());
        HttpGet get1 = new HttpGet("http://api.gateway.com/");
        CloseableHttpResponse response3 = httpClient.execute(get1);
        Header[] headers2 = response2.getAllHeaders();
        Map map = new HashMap();
        for (int i = 0; i < headers2.length; i++) {
            Header header = headers[i];
            if (header.getName().equals("Set-Cookie")) {
                String cookie = header.getValue();
                String mp = cookie.split(";")[0];
                map.put(mp.split("=")[0], mp.split("=")[1]);
                get1.setHeader("Cookie", header.getValue());
                response.setHeader("Cookie", cookie);
                System.out.println("cookies" + response.getHeader("Cookie"));
            }
        }
        System.err.println("get" + get1.getURI());
        InputStream inputStream3 = response3.getEntity().getContent();
        inputStreamReader = new InputStreamReader(inputStream3);
        StringBuffer stringBuffer = new StringBuffer();
        reader = new BufferedReader(inputStreamReader);
        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("href=\"", "href=\"http://api.gateway.com");
            if (line.contains(
                    "e64c7d89f26bd1972efa854d13d7dd61?")) {
                line = line.replace("src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\"", "");
                line = line.replace("data-src=\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=46&d=identicon\"", "src=\"https://www.gravatar.com/avatar/e64c7d89f26bd1972efa854d13d7dd61?s=46&d=identicon\"");
            } else if (line.contains("src=\"/uploads/-/system/appearance/header_logo/1/1-1.png\"")) {
                line = line.replace(" src=\"data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw==\"", "");
                line =
                        line.replace(
                                "data-src=\"/uploads/-/system/appearance/header_logo/1/1-1.png\"",
                                "src=\"http://api.gateway.com/uploads/-/system/appearance/header_logo/1/1-1.png\"");
            } else {
                line = line.replaceAll("src=\"", "src=\"http://api.gateway.com");
            }
            System.out.println(line);
            stringBuffer.append(line);
        }
        Document document = Jsoup.parse(stringBuffer.toString());
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Expose-Headers", "Cookie");
        PrintWriter out = response.getWriter();
        out.println(document.outerHtml());
    }

    class MyCookieSpec extends DefaultCookieSpec {
        @Override
        public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
            String value = header.getValue();
            String prefix = "Expires=";
            if (value.contains(prefix)) {
                String expires = value.substring(value.indexOf(prefix) + prefix.length());
                expires = expires.substring(0, expires.indexOf(";"));
                // String date = DateUtils.formatDate(new Date(expires),"EEE, dd-MMM-yy HH:mm:ss z");
                //value = value.replaceAll(prefix + "\\d{10};", prefix + date + ";");
            }
            header = new BasicHeader(header.getName(), value);
            return super.parse(header, cookieOrigin);
        }
    }

}