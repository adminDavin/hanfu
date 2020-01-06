package com.hanfu.payment.center.utils;

import com.hanfu.payment.center.config.Configure;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

/**
 * @description: 签名
 * @author: ningcs
 * @create: 2019-06-25 14:53
 * 生成返回签名的工具类
 **/
public class Signature {

    public static String getSign(Object o) throws IllegalAccessException {
        ArrayList<String> list = new ArrayList<String>();
        Class cls = o.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.get(o) != null && f.get(o) != "") {
                String name = f.getName();
                XStreamAlias anno = f.getAnnotation(XStreamAlias.class);
                if (anno != null)
                    name = anno.value();
                list.add(name + "=" + f.get(o) + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + Configure.getKey();
        System.out.println("签名数据：" + result);
        result = MD5.MD5Encode(result).toUpperCase();
        return result;
    }

    public static String getSign(Map<String, Object> map) {
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != "") {
                list.add(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        int size = list.size();
        String[] arrayToSort = list.toArray(new String[size]);
        Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(arrayToSort[i]);
        }
        String result = sb.toString();
        result += "key=" + Configure.getKey();
//Util.log("Sign Before MD5:" + result);
        result = MD5.MD5Encode(result).toUpperCase();
//Util.log("Sign Result:" + result);
        return result;
    }

}
