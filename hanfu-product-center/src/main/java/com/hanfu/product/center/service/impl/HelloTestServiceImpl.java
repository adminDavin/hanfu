package com.hanfu.product.center.service.impl;

import org.springframework.stereotype.Service;

import com.hanfu.inner.sdk.product.center.HelloTestService;


@Service("helloTestService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class HelloTestServiceImpl implements HelloTestService {


    @Override
    public void test() {
        System.out.println("sssss");

    }

}
