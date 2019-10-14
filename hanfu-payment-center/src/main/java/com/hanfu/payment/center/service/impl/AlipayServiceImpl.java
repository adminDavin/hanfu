package com.hanfu.payment.center.service.impl;

import org.springframework.stereotype.Service;

import com.hanfu.payment.center.service.AlipayService;

@Service("alipayService")
@org.apache.dubbo.config.annotation.Service(registry = "dubboProductServer")
public class AlipayServiceImpl implements AlipayService {

}
