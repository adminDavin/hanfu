package com.hanfu.payment.center.utils;

import java.util.Random;

/**
 * @description:
 * @author: ningcs
 * @create: 2019-06-25 15:01
 * 生成随机数的工具类
 **/
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
}