package com.hanfu.payment.center.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

public class RandomStringGenerator {

        public static String getRandomStringByLength(int length) {
            String base = "abcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < length; i++) {
                int number = random.nextInt(base.length());
                sb.append(base.charAt(number));
            }
            return sb.toString();
        }

        public static String getRandom()
        {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式

            String format = df.format(new Date());//获取系统当前时间

            int random = (int)((Math.random()*9+1)*100000);//六个随机数字

            String ran = format+random;

            return ran;
        }

        public static String getIp2(HttpServletRequest request) {
            String ip = request.getHeader("X-Forwarded-For");
            if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实ip
                int index = ip.indexOf(",");
                if (index != -1) {
                    return ip.substring(0, index);
                } else {
                    return ip;
                }
            }
            ip = request.getHeader("X-Real-IP");
            if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
                return ip;
            }
            return request.getRemoteAddr();
        }

}
