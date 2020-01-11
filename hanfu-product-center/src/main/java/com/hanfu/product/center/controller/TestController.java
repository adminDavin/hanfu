package com.hanfu.product.center.controller;

import com.hanfu.product.center.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author:gyj
 * @date: 2020/1/9
 * @time: 17:36
 */
@Controller
public class TestController extends Thread {
    @Autowired
    private ServiceImpl service;

    public TestController(ServiceImpl service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.seckill();
    }
    public static void main(String[] args) {
        ServiceImpl service = new ServiceImpl();
        for (int i = 0; i < 50; i++) {
            TestController threadA = new TestController(service);
            threadA.start();
        }
    }
}


