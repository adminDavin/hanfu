package com.hanfu.payment.center.tool;


import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateQrcore {

    /*
     * 获取 token
     * 普通的 get 可获 token
     */
    public  static String getToken() {
        try {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("grant_type", "client_credential");
            map.put("appid","wxfa188a42d843a0b0");
            map.put("secret", "0433593dd1887ea5381e6d01308f81ba");

            String rt = UrlUtil.sendPost("https://api.weixin.qq.com/cgi-bin/token", map);
            System.out.println("what is:"+rt);
            JSONObject json = JSONObject.parseObject(rt);

            if (json.getString("access_token") != null || json.getString("access_token") != "") {
                return json.getString("access_token");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 获取 二维码图片
     *
     */
    public static String getminiqrQr(String accessToken, HttpServletRequest request, HttpServletResponse response, Integer id) throws Exception{
        String p="D://code"; //二维码生产的地址  本地F盘code文件夹
        System.out.println(p);
        String codeUrl=p+"/twoCode.png";
        String twoCodeUrl="twoCode.png";

            URL url = new URL("https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token="+accessToken);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            JSONObject paramJson = new JSONObject();
//            paramJson.put("scene","2");//这就是你二维码里携带的参数 String型  名称不可变
            paramJson.put("path", "pages/order/clerk/clerk?id="+id); //这是设置扫描二维码后跳转的页面
            paramJson.put("width", 430);
//            paramJson.put("is_hyaline", true);
//            paramJson.put("auto_color", true);

            System.out.println("httpURLConnection"+httpURLConnection);
            System.out.println("paramJson.toString()"+paramJson.toString());
            printWriter.write(paramJson.toString());
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new BufferedInputStream(httpURLConnection.getInputStream());
//            OutputStream os = new FileOutputStream(new File(codeUrl));
            int len;
//            byte[] arr = new byte[1024];
//            while ((len = bis.read(arr)) != -1)
//            {
//                os.write(arr, 0, len);
//                os.flush();
//            }
//            os.flush();
//            os.close();

        response.setContentType("image/png");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        OutputStream stream = response.getOutputStream();
        byte[] arr1 = new byte[1024];
        while ((len = bis.read(arr1)) != -1)
        {
            stream.write(arr1, 0, len);
            stream.flush();
        }
        stream.flush();
        stream.close();
        return twoCodeUrl;
    }
}
