package com.hanfu.payment.center.returnutil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


/**
 * request请求工具类
 *
 * @author Oreh
 * @date 2013-10-18
 */
public class RequestUtils {

    private final static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 封装页面参数到map集合中
     * 只适用于get请求
     *
     * @param request
     * @param paramMap
     * @return
     */
    public static Map<String, Object> paramMap(HttpServletRequest request, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        String query = request.getQueryString();
        if (query != null && !"".equals(query)) {//a=aa&b=b,b,b,b&&
            query = query.substring(1, query.length());
            String[] params = query.split("&");
            String[] str = null;
            for (String param : params) {
                if (param.contains("=")) {
                    str = param.split("=");
                    paramMap.put(str[0], str[1]);
                }
            }
        }
        return paramMap;
    }

    /**
     * 封装页面参数到map集合中
     * 适用于get和post请求
     *
     * @param request
     * @param paramMap
     * @return
     */
    public static Map<String, Object> paramMap(HttpServletRequest request, Map<String, Object> paramMap, String[] params) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        for (String param : params) {
            String[] values = request.getParameterValues(param);
            if (values != null && values.length != 0) {
                if (values.length > 1) {
                    paramMap.put(param, values);
                } else {
                    paramMap.put(param, values[0]);
                }
            }
        }
        return paramMap;
    }

    /**
     * 从request中获取IP地址的方法
     *
     * @param request
     * @return
     * @author Oreh
     * @Date 2013-10-24
     * @Time 上午10:10:30
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};

    /***
     * 获取客户端ip地址(可以穿透代理)
     * @param request
     * @return
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }
}
