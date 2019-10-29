package com.hanfu.referral.center.service.impl;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import com.hanfu.inner.sdk.product.center.HelloTestService;
import com.hanfu.referral.center.service.ReferralHelloService;

@Service("referralHelloService")
public class ReferralHelloServiceImpl implements ReferralHelloService {

    @Reference(registry = "dubboProductServer", url = "dubbo://127.0.0.1:1900/com.hanfu.inner.sdk.product.center.HelloTestService")
    private HelloTestService helloTestService;
    
    public void hello() {
        helloTestService.test();
    }
}
