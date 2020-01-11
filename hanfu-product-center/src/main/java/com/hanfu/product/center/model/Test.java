package com.hanfu.product.center.model;

import com.hanfu.product.center.controller.TestController;
import com.hanfu.product.center.service.impl.ServiceImpl;
import org.junit.runner.RunWith;

/**
 * @author:gyj
 * @date: 2020/1/9
 * @time: 17:48
 */
public class Test {

        public static void main(String[] args) {
            ServiceImpl service = new ServiceImpl();
            for (int i = 0; i < 50; i++) {
                TestController threadA = new TestController(service);
                threadA.start();
            }
        }


}
