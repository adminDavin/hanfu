package com.hanfu.user.center.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Subdivision;

public class IpAddress {

    private static DatabaseReader reader;
    private static ReentrantLock lock = new ReentrantLock();

    static {
        load();
    }

    public static String getRemortIP(HttpServletRequest request) {

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
        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }
        return ip;
    }

    public static String findOne(String ip) {
        String[] ipAddrs = find(ip);
        if (ipAddrs != null && ipAddrs.length > 0) {
            StringBuilder addrBuilder = new StringBuilder();
            for (String addr : ipAddrs) {
                addrBuilder.append(addr);
            }
            return addrBuilder.toString();
        }
        return null;
    }

    public static String[] find(String ip) {
        try {
            String addr[] = new String[3];
            InetAddress ipAddress = InetAddress.getByName(ip);
            // 获取查询结果
            CityResponse response = reader.city(ipAddress);
            // 获取国家名称
            Country country = response.getCountry();
            addr[0] = country.getNames().get("zh-CN");
            // 获取省分名称
            Subdivision subdivision = response.getMostSpecificSubdivision();
            addr[1] = subdivision.getNames().get("zh-CN");
            // 获取城市名称
            City city = response.getCity();
            addr[2] = city.getNames().get("zh-CN");
            return addr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void load() {
        lock.lock();
        // 创建 GeoLite2 数据库
        InputStream database = IpAddress.class
                .getResourceAsStream("/files/GeoLite2-City.mmdb");
        // 读取数据库内容
        try {
            reader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            try {
                if (null != database) {
                    database.close();
                }
            } catch (IOException e) {
            }
        }
    }
}

