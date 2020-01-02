package com.hanfu.base.chat.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpUtil {

    private static Logger log = Logger.getLogger(HttpUtil.class.getSimpleName());


    public static final String RES_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";

    public static JSONObject httpsRequest(JSONObject jsonParam) {
        String aToken = "****";
        StringBuffer sb = new StringBuffer();
        try {
            ;
            // 创建url资源
            URL url = new URL(RES_URL + aToken);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            // 转换为字节数组
            byte[] data = (jsonParam.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("contentType", "application/json");
            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 写入请求的字符串
            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();

            System.out.println(conn.getResponseCode());

            // 请求返回的状态
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                System.out.println("连接成功");
                // 请求返回的数据
                InputStream in1 = conn.getInputStream();
                try {
                    String readLine = new String();
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1, "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();
                    System.out.println(sb.toString());

                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.info("error1++" + e1);
                }
            } else {
                System.out.println("error++");
                log.info("error++");
            }

        } catch (Exception e) {
            log.info("error3++" + e);
        }
        log.info(sb + "sb");
        return JSONObject.parseObject(sb.toString());
    }


    public static JSONObject httpsRequest(JSONObject jsonParam, String requestSource) {
        String aToken = "****";
        log.info("HttpUtil.getAccess_token  " + requestSource);
        log.info("HttpUtil.getAccess_token  aToken:   " + aToken);
        StringBuffer sb = new StringBuffer();
        try {
            ;
            // 创建url资源
            URL url = new URL(RES_URL + aToken);
            // 建立http连接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置允许输出
            conn.setDoOutput(true);
            // 设置允许输入
            conn.setDoInput(true);
            // 设置不用缓存
            conn.setUseCaches(false);
            // 设置传递方式
            conn.setRequestMethod("POST");
            // 设置维持长连接
            conn.setRequestProperty("Connection", "Keep-Alive");
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            // 转换为字节数组
            byte[] data = (jsonParam.toString()).getBytes();
            // 设置文件长度
            conn.setRequestProperty("Content-Length", String.valueOf(data.length));
            // 设置文件类型:
            conn.setRequestProperty("contentType", "application/json");
            // 开始连接请求
            conn.connect();
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            // 写入请求的字符串
            out.write((jsonParam.toString()).getBytes());
            out.flush();
            out.close();

            System.out.println(conn.getResponseCode());

            // 请求返回的状态
            if (HttpURLConnection.HTTP_OK == conn.getResponseCode()) {
                System.out.println("连接成功");
                // 请求返回的数据
                InputStream in1 = conn.getInputStream();
                try {
                    String readLine = new String();
                    BufferedReader responseReader = new BufferedReader(new InputStreamReader(in1, "UTF-8"));
                    while ((readLine = responseReader.readLine()) != null) {
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();
                    System.out.println(sb.toString());

                } catch (Exception e1) {
                    e1.printStackTrace();
                    log.info("error1++" + e1);
                }
            } else {
                System.out.println("error++");
                log.info("error++");
            }

        } catch (Exception e) {
            log.info("error3++" + e);
        }
        log.info(sb + "sb");
        return JSONObject.parseObject(sb.toString());
    }

    //传入URL
    public static String getAccess_token(String url) {
        String accessToken = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            accessToken = new String(jsonBytes, "UTF-8");
            System.out.println(accessToken);
            is.close();
        } catch (Exception e) {

            e.printStackTrace();

        }
        JSONObject result = JSONObject.parseObject(accessToken);
        Map<String, String> map = JSONObject.toJavaObject(result, Map.class);
        System.out.println(map.get("accessToken"));
        log.info("accessToken    " + map.get("accessToken"));
        return map.get("accessToken");

    }

}
