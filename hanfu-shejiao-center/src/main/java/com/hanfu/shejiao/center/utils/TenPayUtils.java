package com.hanfu.shejiao.center.utils;

import com.github.pagehelper.util.StringUtil;
import com.hanfu.shejiao.center.config.TenpayConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

/**
 * TenPayUtils
 * @author :zengXianKang
 */
@Component
public class TenPayUtils {
    private static TenPayUtils tenPayUtils;
    @Autowired
    private TenpayConfig tenpayConfig;

    @PostConstruct
    public void init(){
        tenPayUtils = this;
        tenPayUtils.tenpayConfig = this.tenpayConfig;
    }

    /**
     * @Description: 微信支付签名
     *
     * @Param: [paramsMap, charSetName]
     * @return: java.lang.String
     * @Author: zengXianKang
     * @Date: 2019/7/28 
     */
    public String createSign(SortedMap<String, Object> paramsMap, String charSetName) throws UnsupportedEncodingException,
            NoSuchAlgorithmException {
        StringBuffer buffer = new StringBuffer();
        //参数按照ACCSII排序（升序）
        Set set = paramsMap.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if(!key.equals("sign") && StringUtil.isNotEmpty(value)){
                buffer.append(key + "=" + value + "&");
            }
        }
        buffer.append("key=" + tenPayUtils.tenpayConfig.getKey());
        String sign = MDUtils.MD5EncodeForHex(buffer.toString(), charSetName).toUpperCase();
        return sign;
    }
    /**
     * @Description: 组装微信支付请求报文
     *
     * @Param: [paramsMap]
     * @return: java.lang.String
     * @Author: zengXianKang
     * @Date: 2019/7/28
     */
    public static String tenPayXmlInfo(SortedMap<String, Object> paramsMap){
        StringBuffer buffer = new StringBuffer();
        if(paramsMap != null){
            buffer.append("<xml>");
            for(Map.Entry<String, Object> entry : paramsMap.entrySet()){
                buffer.append("<").append(entry.getKey()).append("><![CDATA[").append(entry.getValue()).append("]]></").append(entry.getKey()).append(">");
            }
            buffer.append("</xml>");
        }
        return buffer.toString();
    }

    /**
     * @Description: 请求调用URL
     *
     * @Param: [requestUrl, requestMethod, output]
     * @return: java.lang.String
     * @Author: zengXianKang
     * @Date: 2019/7/28
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String output) throws Exception {
        URL url = new URL(requestUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setRequestMethod(requestMethod);
        if(StringUtil.isNotEmpty(output)){
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(output.getBytes("UTF-8"));
            outputStream.close();
        }
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String str = null;
        StringBuffer buffer = new StringBuffer();
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
        connection.disconnect();
        return buffer.toString();
    }
    /**
     * @Description: 解析xml
     *
     * @Param: [xml, rootName, rowName]
     * @return: java.lang.Object
     * @Author: zengXianKang
     * @Date: 2019/7/28
     */
    public static Object readXml(String xml, String rootName, String rowName){
        XStream xStream = new XStream(new DomDriver());
        xStream.alias(rootName, Map.class);
        xStream.registerConverter(new TenPayUtils.MapEntryConverter(rowName));
        Object object = xStream.fromXML(xml);
        return object;
    }
    /** 
     * @Description: 内部类,readXml专用
     *
     * @Param: 
     * @return: 
     * @Author: zengXianKang
     * @Date: 2019/7/28
     */
    public static class MapEntryConverter implements Converter {
        private String rowName;

        public MapEntryConverter(String rowName) {
            this.rowName = rowName;
        }

        public boolean canConvert(Class clazz) {
            return Map.class.isAssignableFrom(clazz) || LinkedHashMap.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
            this._marshal(value, writer, context);
        }

        private void _marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
            Iterator i$;
            Object object;
            if (value instanceof Map) {
                Map map = (Map) value;

                for (i$ = map.entrySet().iterator(); i$.hasNext(); writer.endNode()) {
                    object = i$.next();
                    Entry entry = (Entry) object;
                    Object _key = entry.getKey();
                    Object _value = entry.getValue();
                    writer.startNode(entry.getKey().toString());
                    if (_value instanceof Map) {
                        this._marshal(_value, writer, context);
                    } else if (_value instanceof List) {
                        this._marshal(_value, writer, context);
                    } else {
                        writer.setValue(entry.getValue().toString());
                    }
                }
            } else if (value instanceof List) {
                List list = (List) value;

                for (i$ = list.iterator(); i$.hasNext(); writer.endNode()) {
                    object = i$.next();
                    writer.startNode(this.rowName);
                    if (!(object instanceof Map) && !(object instanceof List)) {
                        writer.setValue(object.toString());
                    } else {
                        this._marshal(object, writer, context);
                    }
                }
            }

        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            return this._unmarshal(reader, context);
        }

        public Object _unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            Map map = new HashMap();
            List list = new ArrayList();

            boolean isList;
            for (isList = false; reader.hasMoreChildren(); reader.moveUp()) {
                reader.moveDown();
                String nodeName = reader.getNodeName();
                if (reader.hasMoreChildren()) {
                    if (isList) {
                        list.add(this._unmarshal(reader, context));
                    } else if (map.containsKey(nodeName)) {
                        isList = true;
                        list.add(map.remove(nodeName));
                        list.add(this._unmarshal(reader, context));
                    } else if (this.rowName.equals(nodeName)) {
                        isList = true;
                        list.add(this._unmarshal(reader, context));
                    } else {
                        map.put(nodeName, this._unmarshal(reader, context));
                    }
                } else {
                    String value = reader.getValue();
                    if (isList) {
                        list.add(value);
                    } else if (map.containsKey(nodeName)) {
                        isList = true;
                        list.add(map.remove(nodeName));
                        list.add(value);
                    } else if (this.rowName.equals(nodeName)) {
                        isList = true;
                        list.add(value);
                    } else {
                        map.put(nodeName, value);
                    }
                }
            }
            return isList ? list : map;
        }
    }
}