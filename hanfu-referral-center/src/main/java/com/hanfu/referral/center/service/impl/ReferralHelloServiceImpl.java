package com.hanfu.referral.center.service.impl;

import com.hanfu.inner.sdk.product.center.HelloTestService;
import com.hanfu.referral.center.service.ReferralHelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("referralHelloService")
public class ReferralHelloServiceImpl implements ReferralHelloService {

    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.product.center.HelloTestService")
    private HelloTestService helloTestService;


    public void hello() {
        helloTestService.test();
    }

    @Override
    public String getYear() {
        Integer year = LocalDateTime.now().getYear();
        if (year < 1900) {
            return "未知";
        }
        Integer start = 1900;
        String[] years = new String[]{
                "鼠", "牛", "虎", "兔",
                "龙", "蛇", "马", "羊",
                "猴", "鸡", "狗", "猪"
        };
        return years[(year - start) % years.length];
    }
}
